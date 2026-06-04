package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:derby:bookingsystemDB;create=true";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}