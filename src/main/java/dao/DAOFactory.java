package dao;

public class DAOFactory {

    private static UserDAO userDAO;
    private static CarDAO carDAO;
    private static BookingDAO bookingDAO;

    public static UserDAO getUserDAO() {
        if (userDAO == null) userDAO = new UserDAOImpl();
        return userDAO;
    }

    public static CarDAO getCarDAO() {
        if (carDAO == null) carDAO = new CarDAOImpl();
        return carDAO;
    }

    public static BookingDAO getBookingDAO() {
        if (bookingDAO == null) bookingDAO = new BookingDAOImpl();
        return bookingDAO;
    }
}