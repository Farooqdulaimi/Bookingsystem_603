package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabs;
    private User loggedInAdmin;

    public MainFrame(User admin) {
        this.loggedInAdmin = admin;

        setTitle("Admin Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeManager.getPrimaryColor());

        JLabel title = new JLabel("Admin Dashboard - " + admin.getUsername(), SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        header.add(title, BorderLayout.WEST);

        JButton themeBtn = new JButton("Toggle Theme");
        themeBtn.addActionListener(e -> {
            ThemeManager.toggleTheme();
            ThemeManager.applyTheme(getContentPane());
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        rightButtons.setOpaque(false);
        rightButtons.add(themeBtn);
        rightButtons.add(logoutBtn);
        header.add(rightButtons, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.addTab("Cars", new CarsPanel());
        tabs.addTab("Users", new CustomerAdminPanel());
        tabs.addTab("Create User", new AdminCreateUserPanel());
        tabs.addTab("Create Booking", new AdminBookingPanel());
        tabs.addTab("All Bookings", new AdminBookingsPanel());

        add(tabs, BorderLayout.CENTER);
        ThemeManager.applyTheme(getContentPane());
    }

    public void refreshAdminBookings() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals("All Bookings")) {
                Component c = tabs.getComponentAt(i);
                if (c instanceof AdminBookingsPanel) {
                    ((AdminBookingsPanel) c).refreshBookings();
                }
            }
        }
    }
}
