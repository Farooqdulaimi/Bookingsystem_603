package dao;

import model.Booking;
import java.util.ArrayList;

public interface BookingDAO {

    void addBooking(Booking booking);

    void deleteBooking(int id);

    void cancelBooking(int id);

    ArrayList<Booking> getAllBookings();

    Booking getBookingById(int id);

    ArrayList<Booking> getBookingsByUserId(int userId);
}