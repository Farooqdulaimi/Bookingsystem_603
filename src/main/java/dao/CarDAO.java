package dao;
import java.sql.Connection;
import dao.DBConnection;
import model.Car;
import java.util.ArrayList;

public interface CarDAO {

    void createTable();

    void addCar(Car car);

    Car getCarById(int id);

    ArrayList<Car> getAllCars();

    void updateCar(Car car);

    void deleteCar(int id);
}
