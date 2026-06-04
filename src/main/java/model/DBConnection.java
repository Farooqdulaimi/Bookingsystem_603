package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:derby:carRentalDB;create=true";

    private DBConnection() {
        try {
            // Connect to Derby database
            connection = DriverManager.getConnection(URL);

            System.out.println("Derby connected successfully!");

        } catch (SQLException e) {
            System.err.println("SQL error while connecting to Derby!");
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}