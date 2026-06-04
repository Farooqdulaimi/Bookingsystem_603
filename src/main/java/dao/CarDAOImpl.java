package dao;

import model.Car;

import java.sql.*;
import java.util.ArrayList;

public class CarDAOImpl implements CarDAO {

    private Connection conn;

    public CarDAOImpl() {
        this.conn = DBConnection.getInstance().getConnection();

        if (this.conn == null) {
            throw new RuntimeException("Database connection is NULL in CarDAOImpl");
        }

        createTable();
    }

    @Override
    public void createTable() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "CARS", null);

            if (!rs.next()) {

                String sql =
                        "CREATE TABLE cars (" +
                                "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                                "brand VARCHAR(255), " +
                                "model VARCHAR(255), " +
                                "car_year INT, " +
                                "price_per_day DOUBLE, " +
                                "available BOOLEAN" +
                                ")";

                conn.createStatement().execute(sql);
                System.out.println("CARS table created.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCar(Car car) {
        try {
            String sql = "INSERT INTO cars (brand, model, car_year, price_per_day, available) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getPricePerDay());
            ps.setBoolean(5, car.isAvailable());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCarById(int id) {
        try {
            String sql = "SELECT * FROM cars WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("car_year"),
                        rs.getDouble("price_per_day"),
                        rs.getBoolean("available")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM cars";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                list.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("car_year"),
                        rs.getDouble("price_per_day"),
                        rs.getBoolean("available")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateCar(Car car) {
        try {
            String sql = "UPDATE cars SET brand=?, model=?, car_year=?, price_per_day=?, available=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getPricePerDay());
            ps.setBoolean(5, car.isAvailable());
            ps.setInt(6, car.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(int id) {
        try {
            String sql = "DELETE FROM cars WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}