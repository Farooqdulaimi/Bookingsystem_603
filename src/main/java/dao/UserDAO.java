package dao;
import java.sql.Connection;
import dao.DBConnection;
import model.User;
import java.util.ArrayList;

public interface UserDAO {

    void createTable();

    void addUser(User user);

    User getUserByUsername(String username);

    User getUserByUsernameAndPassword(String username, String password);

    ArrayList<User> getAllUsers();

    void deleteUser(int id);
}
