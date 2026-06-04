package ui;

import model.Booking;
import service.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminBookingsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private BookingService bookingService = new BookingService();

    public AdminBookingsPanel() {

        setLayout(new BorderLayout());
        UITheme.applyPanel(this);

        JLabel title = new JLabel("ADMIN BOOKING DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(UITheme.PRIMARY);
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // SEARCH
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = UITheme.searchField();
        top.add(new JLabel("Search: "));
        top.add(searchField);
        add(top, BorderLayout.BEFORE_FIRST_LINE);

        model = new DefaultTableModel(
                new Object[]{"ID", "User", "Car", "Start", "End", "Price", "Status"}, 0
        );

        table = new JTable(model);
        UITheme.styleTable(table);
        UITheme.applyStatusRenderer(table, 6);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton refresh = UITheme.primaryButton("Refresh");
        JButton cancel = UITheme.dangerButton("Cancel");

        bottom.add(refresh);
        bottom.add(cancel);

        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> load());
        cancel.addActionListener(e -> cancel());

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }
        });

        load();
    }

    private void load() {
        model.setRowCount(0);

        for (Booking b : bookingService.getAllBookings()) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getUserId(),
                    b.getCarId(),
                    b.getStartDate(),
                    b.getEndDate(),
                    b.getTotalPrice(),
                    b.getStatus()
            });
        }
    }

    private void search() {
        String key = searchField.getText().toLowerCase();
        model.setRowCount(0);

        for (Booking b : bookingService.getAllBookings()) {
            if (String.valueOf(b.getId()).contains(key) ||
                String.valueOf(b.getUserId()).contains(key)) {

                model.addRow(new Object[]{
                        b.getId(),
                        b.getUserId(),
                        b.getCarId(),
                        b.getStartDate(),
                        b.getEndDate(),
                        b.getTotalPrice(),
                        b.getStatus()
                });
            }
        }
    }

    private void cancel() {
        int row = table.getSelectedRow();

        if (row == -1) return;

        int id = (int) model.getValueAt(row, 0);
        bookingService.cancelBooking(id);
        load();
    }

    public void refreshBookings() {
        load();
    }
    
}
