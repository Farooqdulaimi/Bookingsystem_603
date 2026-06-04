package ui;

import model.User;
import javax.swing.*;
import java.awt.*;

public class CustomerMainFrame extends JFrame {

    private JTabbedPane tabs;
    private User loggedInUser;

    public CustomerMainFrame(User user) {
        this.loggedInUser = user;

        setTitle("Customer Dashboard - " + user.getUsername());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeManager.getPrimaryColor());

        JLabel title = new JLabel("Welcome, " + user.getUsername());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        header.add(title, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        header.add(logoutBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.addTab("Available Cars", new CarsCustomerPanel());
        tabs.addTab("Create Booking", new BookingListPanel(user));
        tabs.addTab("My Bookings", new CustomerBookingsPanel(user));

        add(tabs, BorderLayout.CENTER);
    }

    public void refreshCustomerBookings() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals("My Bookings")) {
                java.awt.Component c = tabs.getComponentAt(i);
                if (c instanceof CustomerBookingsPanel) {
                    ((CustomerBookingsPanel) c).refreshBookings();
                }
            }
        }
    }
}