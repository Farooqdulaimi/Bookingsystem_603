import model.Car;
import org.junit.Before;
import org.junit.Test;
import service.CarService;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CarServiceTest {

    private CarService service;

    @Before
    public void setUp() {
        service = new CarService();
    }

    @Test
    public void testGetAllCars() {
        ArrayList<Car> cars = service.getAllCars();
        assertNotNull(cars);
    }

    @Test
    public void testCarAvailabilityUpdate() {
        ArrayList<Car> cars = service.getAllCars();

        if (!cars.isEmpty()) {
            Car c = cars.get(0);
            boolean oldStatus = c.isAvailable();

            c.setAvailable(!oldStatus);
            service.updateCar(c);

            Car updated = service.getCarById(c.getId());
            assertEquals(!oldStatus, updated.isAvailable());
        }
    }
}