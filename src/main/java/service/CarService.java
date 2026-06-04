package service;

import dao.CarDAO;
import dao.CarDAOImpl;
import model.Car;

import java.util.ArrayList;

public class CarService {

    private CarDAO carDAO = new CarDAOImpl();

    public void addCar(Car car) {
        carDAO.addCar(car);
    }

    public Car getCarById(int id) {
        return carDAO.getCarById(id);
    }

    public ArrayList<Car> getAllCars() {
        return carDAO.getAllCars();
    }

    public void updateCar(Car car) {
        carDAO.updateCar(car);
    }

    public void deleteCar(int id) {
        carDAO.deleteCar(id);
    }
}
