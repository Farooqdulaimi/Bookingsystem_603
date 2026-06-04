package ui;

import model.Booking;
import model.Car;
import model.User;
import service.BookingService;
import service.CarService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingListPanel extends JPanel {

    private JComboBox<String> carCombo;
    private JTextField startField;
    private JTextField endField;
    private JLabel priceLabel;

    private CarService carService = new CarService();
    private BookingService bookingService = new BookingService();
    private User loggedInUser;

    public BookingListPanel(User user) {
        this.loggedInUser = user;

        setLayout(new GridBagLayout());
        setBackground(ThemeManager.getBackgroundColor());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Booking", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Car:"), gbc);

        gbc.gridx = 1;
        carCombo = new JComboBox<>();
        add(carCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        startField = new JTextField(15);
        add(startField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("End Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        endField = new JTextField(15);
        add(endField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        priceLabel = new JLabel("Total Price: $0.00");
        add(priceLabel, gbc);

        gbc.gridx = 1;
        JButton bookBtn = new JButton("Book Now");
        add(bookBtn, gbc);

        loadCars();

        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePrice(); }
            public void removeUpdate(DocumentEvent e) { updatePrice(); }
            public void changedUpdate(DocumentEvent e) { updatePrice(); }
        };

        startField.getDocument().addDocumentListener(dl);
        endField.getDocument().addDocumentListener(dl);

        bookBtn.addActionListener(e -> createBooking());

        ThemeManager.applyTheme(this);
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

    private void updatePrice() {
        try {
            if (carCombo.getSelectedItem() == null) return;

            LocalDate start = LocalDate.parse(startField.getText().trim());
            LocalDate end = LocalDate.parse(endField.getText().trim());

            if (!end.isAfter(start)) return;

            int carId = Integer.parseInt(carCombo.getSelectedItem().toString().split(" - ")[0]);

            double pricePerDay = carService.getCarById(carId).getPricePerDay();

            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);

            priceLabel.setText("Total Price: $" + (pricePerDay * days));

        } catch (Exception ignored) {
            // safe ignore for typing
        }
    }

   private void createBooking() {
    try {

        if (startField.getText().trim().isEmpty() || endField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both start and end dates",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // ✅ ONLY ONE carId (keep this one)
        int carId = Integer.parseInt(
                carCombo.getSelectedItem().toString().split(" - ")[0]
        );

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
                loggedInUser.getId(),
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
        JOptionPane.showMessageDialog(
                this,
                "Unable to create booking. Please check your inputs.",
                "Booking Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
   }
}
    
