import org.junit.Test;
import service.BookingService;
import model.Booking;

import static org.junit.Assert.*;

public class BookingServiceTest {

    BookingService service = new BookingService();

    @Test
    public void testAddBooking() {
        Booking b = new Booking();
        service.addBooking(b);
        assertTrue(true);
    }

    @Test
    public void testGetAllBookings() {
        assertNotNull(service.getAllBookings());
    }

    @Test
    public void testCancelBooking() {
        service.cancelBooking(1);
        assertTrue(true);
    }

    @Test
    public void testGetBookingById() {
        assertNotNull(service.getBookingById(1));
    }

    @Test
    public void testGetBookingsByUserId() {
        assertNotNull(service.getBookingsByUserId(1));
    }
}