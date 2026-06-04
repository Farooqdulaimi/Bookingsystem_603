package ui;

import service.CarService;
import service.CustomerService;
import service.BookingService;

import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {

    private CarService carService = new CarService();
    private CustomerService customerService = new CustomerService();
    private BookingService bookingService = new BookingService();

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Reports & Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid.setBackground(Color.WHITE);

        grid.add(createStatCard("Total Cars", String.valueOf(carService.getAllCars().size())));
        grid.add(createStatCard("Available Cars", String.valueOf(carService.getAllCars().stream().filter(c -> c.isAvailable()).count())));
        grid.add(createStatCard("Total Customers", String.valueOf(customerService.getAllCustomers().size())));
        grid.add(createStatCard("Total Bookings", String.valueOf(bookingService.getAllBookings().size())));
        grid.add(createStatCard("Total Revenue", "$" + calculateRevenue()));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(new Color(0, 102, 204));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private double calculateRevenue() {
        return bookingService.getAllBookings()
                .stream()
                .mapToDouble(b -> b.getTotalPrice())
                .sum();
    }
}
