package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();

    public void addCustomer(String name, String email, String phone) {
        Customer c = new Customer(name, email, phone);
        customerDAO.addCustomer(c);
    }

    public void updateCustomer(int id, String name, String email, String phone) {
        Customer c = new Customer(id, name, email, phone);
        customerDAO.updateCustomer(c);
    }

    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }
}
