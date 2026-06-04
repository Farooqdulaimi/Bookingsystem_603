import model.User;
import org.junit.Before;
import org.junit.Test;
import service.UserService;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService service;

    @Before
    public void setUp() {
        service = new UserService();
    }

    @Test
    public void testGetAllUsers() {
        ArrayList<User> users = service.getAllUsers();
        assertNotNull(users);
    }

    @Test
    public void testCustomerExists() {
        ArrayList<User> users = service.getAllUsers();

        boolean found = false;
        for (User u : users) {
            if ("customer".equalsIgnoreCase(u.getRole())) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }
}