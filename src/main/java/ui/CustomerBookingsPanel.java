package ui;

import model.Booking;
import model.User;
import service.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerBookingsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private BookingService bookingService = new BookingService();
    private int userId;

    private JTextField searchField;

    public CustomerBookingsPanel(User user) {
        this.userId = user.getId();

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        // TITLE BAR
        JLabel title = new JLabel("My Bookings Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(UITheme.PRIMARY);
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // SEARCH BAR
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = UITheme.searchField();
        JButton searchBtn = UITheme.primaryButton("Search");

        top.add(new JLabel("Search Booking ID: "));
        top.add(searchField);
        top.add(searchBtn);

        add(top, BorderLayout.BEFORE_FIRST_LINE);

        // TABLE
        model = new DefaultTableModel(
                new Object[]{"ID", "Car ID", "Start", "End", "Price", "Status"}, 0
        );

        table = new JTable(model);
        UITheme.styleTable(table);
        UITheme.applyStatusRenderer(table, 5);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // BUTTONS
        JPanel bottom = new JPanel();

        JButton refreshBtn = UITheme.primaryButton("Refresh");
        JButton cancelBtn = UITheme.dangerButton("Cancel Booking");

        bottom.add(refreshBtn);
        bottom.add(cancelBtn);

        add(bottom, BorderLayout.SOUTH);

        // EVENTS
        refreshBtn.addActionListener(e -> loadBookings());
        cancelBtn.addActionListener(e -> cancelSelectedBooking());
        searchBtn.addActionListener(e -> search());

        loadBookings();
    }

    private void loadBookings() {
        model.setRowCount(0);

        ArrayList<Booking> list = bookingService.getBookingsByUserId(userId);

        for (Booking b : list) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getCarId(),
                    b.getStartDate(),
                    b.getEndDate(),
                    b.getTotalPrice(),
                    b.getStatus()
            });
        }
    }

    private void search() {
        String key = searchField.getText().trim();
        model.setRowCount(0);

        for (Booking b : bookingService.getBookingsByUserId(userId)) {
            if (String.valueOf(b.getId()).contains(key)) {
                model.addRow(new Object[]{
                        b.getId(),
                        b.getCarId(),
                        b.getStartDate(),
                        b.getEndDate(),
                        b.getTotalPrice(),
                        b.getStatus()
                });
            }
        }
    }

    private void cancelSelectedBooking() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String status = model.getValueAt(row, 5).toString();

        if ("CANCELLED".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Already cancelled");
            return;
        }

        bookingService.cancelBooking(id);

        JOptionPane.showMessageDialog(this, "Booking cancelled successfully");

        loadBookings();
    }

    public void refreshBookings() {
        loadBookings();
    }
}