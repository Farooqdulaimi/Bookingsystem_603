package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private Connection conn;

    public UserDAOImpl() {

        // 🔥 FIXED CONNECTION LINE
        conn = DBConnection.getInstance().getConnection();

        // safety check (prevents silent crash)
        if (conn == null) {
            throw new RuntimeException("Database connection failed (conn is NULL)");
        }

        createTable();
        createDefaultAdmin();
    }

    @Override
    public void createTable() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "USERS", null);

            if (!rs.next()) {
                String sql = "CREATE TABLE users (" +
                        "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                        "username VARCHAR(255) NOT NULL, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "email VARCHAR(255), " +
                        "phone VARCHAR(50), " +
                        "role VARCHAR(50) NOT NULL" +
                        ")";

                conn.createStatement().execute(sql);
                System.out.println("USERS table created.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultAdmin() {
        try {
            String checkSql = "SELECT COUNT(*) FROM users WHERE username='admin'";
            ResultSet rs = conn.createStatement().executeQuery(checkSql);

            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql =
                        "INSERT INTO users (username, password, email, phone, role) " +
                        "VALUES ('admin','admin','admin@example.com','0000000000','admin')";

                conn.createStatement().executeUpdate(insertSql);
                System.out.println("Default admin account created.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        try {
            String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleteUser(int id) {
        try {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}