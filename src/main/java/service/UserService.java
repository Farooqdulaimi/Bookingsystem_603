package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import java.util.ArrayList;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();

    // LOGIN
    public User login(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    // GENERIC USER CREATION (AdminCreateUserPanel uses this)
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    // CUSTOMER SIGNUP (SignupFrame uses this)
    public void addCustomer(String username, String password, String email, String phone) {
        User u = new User(0, username, password, email, phone, "customer");
        userDAO.addUser(u);
    }

    // ADMIN CREATION (AdminCreateUserPanel can use this)
    public void addAdmin(String username, String password, String email, String phone) {
        User u = new User(0, username, password, email, phone, "admin");
        userDAO.addUser(u);
    }

    // GET USER BY USERNAME
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    // GET ALL USERS
    public ArrayList<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // DELETE USER
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
