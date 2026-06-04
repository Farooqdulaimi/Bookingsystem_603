package ui;

import model.Car;
import service.CarService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CarsAdminPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField brandField, modelField, yearField, priceField;
    private JCheckBox availableCheck;
    private CarService carService = new CarService();

    public CarsAdminPanel() {
        setLayout(new BorderLayout());
        setBackground(ThemeManager.getBackgroundColor());

        JLabel title = new JLabel("Manage Cars", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(ThemeManager.getPrimaryColor());
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"ID", "Brand", "Model", "Year", "Price/Day", "Available"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(ThemeManager.getPanelColor());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        brandField = new JTextField(12);
        modelField = new JTextField(12);
        yearField = new JTextField(6);
        priceField = new JTextField(8);
        availableCheck = new JCheckBox("Available");

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1; form.add(brandField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; form.add(modelField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; form.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; form.add(new JLabel("Price/Day:"), gbc);
        gbc.gridx = 1; form.add(priceField, gbc);

        gbc.gridx = 1; gbc.gridy = 4; form.add(availableCheck, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        form.add(btnPanel, gbc);

        add(form, BorderLayout.SOUTH);

        loadCars();

        addBtn.addActionListener(e -> addCar());
        updateBtn.addActionListener(e -> updateCar());
        deleteBtn.addActionListener(e -> deleteCar());

        ThemeManager.applyTheme(this);
    }

    private void loadCars() {
        model.setRowCount(0);
        ArrayList<Car> cars = carService.getAllCars();
        for (Car c : cars) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getYear(),
                    c.getPricePerDay(),
                    c.isAvailable() ? "Yes" : "No"
            });
        }
    }

    private void addCar() {
        try {
            String brand = brandField.getText().trim();
            String modelName = modelField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            boolean available = availableCheck.isSelected();

            if (brand.isEmpty() || modelName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brand and Model cannot be empty");
                return;
            }

            Car car = new Car(0, brand, modelName, year, price, available);
            carService.addCar(car);

            loadCars();
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void updateCar() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a car to update");
            return;
        }
        try {
            int id = (int) model.getValueAt(row, 0);
            String brand = brandField.getText().trim();
            String modelName = modelField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            boolean available = availableCheck.isSelected();

            Car car = carService.getCarById(id);
            car.setBrand(brand);
            car.setModel(modelName);
            car.setYear(year);
            car.setPricePerDay(price);
            car.setAvailable(available);

            carService.updateCar(car);
            loadCars();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void deleteCar() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a car to delete");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        carService.deleteCar(id);
        loadCars();
    }

    private void clearFields() {
        brandField.setText("");
        modelField.setText("");
        yearField.setText("");
        priceField.setText("");
        availableCheck.setSelected(false);
    }
}
