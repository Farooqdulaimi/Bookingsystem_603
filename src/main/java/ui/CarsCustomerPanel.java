package ui;

import model.Car;
import service.CarService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CarsCustomerPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private CarService carService = new CarService();

    public CarsCustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(ThemeManager.getBackgroundColor());

        JLabel title = new JLabel("Available Cars", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setOpaque(true);
        title.setBackground(ThemeManager.getPrimaryColor());
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"ID", "Brand", "Model", "Year", "Price/Day", "Available"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // customer cannot edit
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadCars();
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
}
