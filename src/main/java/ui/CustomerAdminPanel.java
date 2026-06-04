package ui;

import model.Booking;
import model.Car;
import model.User;
import service.BookingService;
import service.CarService;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerAdminPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private UserService userService = new UserService();
    private BookingService bookingService = new BookingService();
    private CarService carService = new CarService();

    public CustomerAdminPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Manage Customers", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(30, 144, 255));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"ID", "Username", "Email", "Phone", "Role"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete Customer");
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        deleteBtn.addActionListener(e -> deleteCustomer());

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245, 247, 250));
        bottom.add(deleteBtn);

        add(bottom, BorderLayout.SOUTH);

        loadCustomers();
    }

    private void loadCustomers() {
        model.setRowCount(0);

        ArrayList<User> users = userService.getAllUsers();
        for (User u : users) {
            if (u.getRole().equals("customer")) {
                model.addRow(new Object[]{
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getPhone(),
                        u.getRole()
                });
            }
        }
    }

    private void deleteCustomer() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer first");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this customer and all their bookings?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        // Delete all bookings for this customer
        ArrayList<Booking> bookings = bookingService.getBookingsByUserId(id);
        for (Booking b : bookings) {
            Car car = carService.getCarById(b.getCarId());
            car.setAvailable(true);
            carService.updateCar(car);

            bookingService.deleteBooking(b.getId());
        }

        // Delete customer
        userService.deleteUser(id);

        JOptionPane.showMessageDialog(this, "Customer deleted successfully");

        loadCustomers();
    }
}
