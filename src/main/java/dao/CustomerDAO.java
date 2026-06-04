package dao;

import model.Customer;
import java.util.List;
import java.sql.Connection;
import dao.DBConnection;
public interface CustomerDAO {

    void createTable();

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);
}
