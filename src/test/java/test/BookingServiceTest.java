import model.Booking;
import org.junit.Before;
import org.junit.Test;
import service.BookingService;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BookingServiceTest {

    private BookingService service;

    @Before
    public void setUp() {
        service = new BookingService();
    }

    @Test
    public void testAddBooking() {
        Booking b = new Booking(
                0,
                1,
                1,
                Date.valueOf("2026-06-10"),
                Date.valueOf("2026-06-12"),
                200.0,
                "ACTIVE"
        );

        service.addBooking(b);

        ArrayList<Booking> list = service.getAllBookings();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testCancelBooking() {
        Booking b = new Booking(
                0,
                1,
                1,
                Date.valueOf("2026-06-10"),
                Date.valueOf("2026-06-12"),
                200.0,
                "ACTIVE"
        );

        service.addBooking(b);

        ArrayList<Booking> list = service.getAllBookings();
        int id = list.get(list.size() - 1).getId();

        service.cancelBooking(id);

        Booking updated = service.getBookingById(id);
        assertEquals("CANCELLED", updated.getStatus());
    }

    @Test
    public void testGetAllBookingsNotNull() {
        ArrayList<Booking> list = service.getAllBookings();
        assertNotNull(list);
    }
}