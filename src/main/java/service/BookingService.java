package service;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import model.Booking;

import java.util.ArrayList;

public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAOImpl();

    public void addBooking(Booking booking) {
        bookingDAO.addBooking(booking);
    }

    public void deleteBooking(int id) {
        bookingDAO.deleteBooking(id);
    }

    public void cancelBooking(int id) {
        bookingDAO.cancelBooking(id);
    }

    public ArrayList<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }

    public ArrayList<Booking> getBookingsByUserId(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }
}