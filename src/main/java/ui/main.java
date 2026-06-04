package ui;

import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.SwingUtilities;

public class main {

    public static void main(String[] args) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            System.out.println("Application started successfully!");
            System.out.println("Database is working!");

            // Start GUI (THIS WAS MISSING)
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });

        } catch (Exception e) {
            System.out.println("Startup Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}