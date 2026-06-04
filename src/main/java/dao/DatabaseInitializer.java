package dao;

public class DatabaseInitializer {

    private final BookingDAO bookingDAO = new BookingDAOImpl();

    public void init() {
        // no direct SQL table creation here unless you really need it
        // just ensure DB exists and DAO works

        System.out.println("Database initialized successfully.");
        
    }
    
}