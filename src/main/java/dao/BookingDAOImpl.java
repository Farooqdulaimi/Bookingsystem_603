package dao;

import model.Booking;

import java.sql.*;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    private final Connection conn;

    public BookingDAOImpl() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public void addBooking(Booking b) {
        String sql = "INSERT INTO BOOKINGS (CAR_ID, USER_ID, START_DATE, END_DATE, TOTAL_PRICE, STATUS) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getCarId());
            ps.setInt(2, b.getUserId());
            ps.setDate(3, b.getStartDate());
            ps.setDate(4, b.getEndDate());
            ps.setDouble(5, b.getTotalPrice());
            ps.setString(6, b.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBooking(int id) {
        String sql = "DELETE FROM BOOKINGS WHERE ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelBooking(int id) {
        String sql = "UPDATE BOOKINGS SET STATUS='CANCELLED' WHERE ID=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM BOOKINGS WHERE ID=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> list = new ArrayList<>();

        String sql = "SELECT * FROM BOOKINGS";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public ArrayList<Booking> getBookingsByUserId(int userId) {
        ArrayList<Booking> list = new ArrayList<>();

        String sql = "SELECT * FROM BOOKINGS WHERE USER_ID=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // helper mapper
    private Booking map(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("ID"),
                rs.getInt("CAR_ID"),
                rs.getInt("USER_ID"),
                rs.getDate("START_DATE"),
                rs.getDate("END_DATE"),
                rs.getDouble("TOTAL_PRICE"),
                rs.getString("STATUS")
        );
    }
}