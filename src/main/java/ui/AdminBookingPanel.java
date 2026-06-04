package ui;

import model.Booking;
import model.Car;
import model.User;
import service.BookingService;
import service.CarService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminBookingPanel extends JPanel {

    private JComboBox<String> carCombo;
    private JComboBox<String> userCombo;
    private JTextField startField;
    private JTextField endField;
    private JLabel priceLabel;

    private CarService carService = new CarService();
    private BookingService bookingService = new BookingService();
    private UserService userService = new UserService();

    public AdminBookingPanel() {

        setLayout(new GridBagLayout());
        setBackground(ThemeManager.getBackgroundColor());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Admin Create Booking", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Customer:"), gbc);

        gbc.gridx = 1;
        userCombo = new JComboBox<>();
        add(userCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Car:"), gbc);

        gbc.gridx = 1;
        carCombo = new JComboBox<>();
        add(carCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        startField = new JTextField(15);
        add(startField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("End Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        endField = new JTextField(15);
        add(endField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        priceLabel = new JLabel("Total Price: $0.00");
        add(priceLabel, gbc);

        gbc.gridx = 1;
        JButton bookBtn = new JButton("Create Booking");
        add(bookBtn, gbc);

        loadUsers();
        loadCars();

        bookBtn.addActionListener(e -> createBooking());

        ThemeManager.applyTheme(this);
    }

    private void loadUsers() {
        userCombo.removeAllItems();

        ArrayList<User> users = userService.getAllUsers();
        for (User u : users) {
            if ("customer".equalsIgnoreCase(u.getRole())) {
                userCombo.addItem(u.getId() + " - " + u.getUsername());
            }
        }
    }

    private void loadCars() {
        carCombo.removeAllItems();

        ArrayList<Car> cars = carService.getAllCars();
        for (Car c : cars) {
            if (c.isAvailable()) {
                carCombo.addItem(c.getId() + " - " + c.getBrand() + " " + c.getModel());
            }
        }
    }

    private void createBooking() {
        try {
            int carId = Integer.parseInt(carCombo.getSelectedItem().toString().split(" - ")[0]);
            int userId = Integer.parseInt(userCombo.getSelectedItem().toString().split(" - ")[0]);

            LocalDate start = LocalDate.parse(startField.getText().trim());
            LocalDate end = LocalDate.parse(endField.getText().trim());

            if (!end.isAfter(start)) {
                JOptionPane.showMessageDialog(this, "End date must be after start date");
                return;
            }

            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
            double price = carService.getCarById(carId).getPricePerDay() * days;

            Booking booking = new Booking(
                    0,
                    carId,
                    userId,
                    Date.valueOf(start),
                    Date.valueOf(end),
                    price,
                    "ACTIVE"
            );

            bookingService.addBooking(booking);

            Car car = carService.getCarById(carId);
            car.setAvailable(false);
            carService.updateCar(car);

            JOptionPane.showMessageDialog(this, "Booking created successfully!");

            loadCars();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}