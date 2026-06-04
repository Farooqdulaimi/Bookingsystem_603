package ui;

import model.User;

import javax.swing.*;

public class CustomerDashboard extends JFrame {

    private JTabbedPane tabs;

    public CustomerDashboard(User customer) {

        setTitle("Customer Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabs = new JTabbedPane();

        tabs.add("Available Cars", new CarsCustomerPanel());
        tabs.add("Make Booking", new BookingListPanel(customer));
        tabs.add("My Bookings", new CustomerBookingsPanel(customer));

        add(tabs);
    }
}
