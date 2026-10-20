package carshop.inventory;

import carshop.vehicles.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** In-memory inventory operations for the car shop. */
public class CarShop {
    private final List<Car> inventory = new ArrayList<>();

    public void addCarToInventory(Car car) {
        inventory.add(Objects.requireNonNull(car, "Car cannot be null."));
    }

    /** Returns a read-only view so callers cannot bypass inventory operations. */
    public List<Car> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public long getTotalInventoryValue() {
        long total = 0L;
        for (Car car : inventory) {
            total += car.getPrice();
        }
        return total;
    }

    public List<Car> findCarsByMake(String make) {
        List<Car> result = new ArrayList<>();
        if (make == null || make.isBlank()) {
            return result;
        }
        String requestedMake = make.trim();
        for (Car car : inventory) {
            if (car.getMake().equalsIgnoreCase(requestedMake)) {
                result.add(car);
            }
        }
        return result;
    }

    public Car getMostExpensiveCar() {
        if (inventory.isEmpty()) {
            return null;
        }
        Car mostExpensive = inventory.get(0);
        for (Car car : inventory) {
            if (car.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = car;
            }
        }
        return mostExpensive;
    }

    public Car getCheapestCar() {
        if (inventory.isEmpty()) {
            return null;
        }
        Car cheapest = inventory.get(0);
        for (Car car : inventory) {
            if (car.getPrice() < cheapest.getPrice()) {
                cheapest = car;
            }
        }
        return cheapest;
    }

    public boolean removeCar(Car car) {
        return car != null && inventory.remove(car);
    }

    public double getAveragePrice() {
        if (inventory.isEmpty()) {
            return 0.0;
        }
        return (double) getTotalInventoryValue() / inventory.size();
    }
}
