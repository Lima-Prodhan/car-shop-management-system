package carshop.users;

import carshop.vehicles.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Stores a customer's profile and in-memory purchase history. */
public class Customer implements User {
    private String name;
    private String address;
    private String contactInfo;
    private final List<Car> purchasedCars = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setPurchasedCarsEmpty() {
        purchasedCars.clear();
    }

    public void updatePurchasedCars(Car carToAdd) {
        purchasedCars.add(Objects.requireNonNull(carToAdd, "Purchased car cannot be null."));
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    /** Returns a read-only view of the purchase history. */
    public List<Car> getPurchasedCars() {
        return Collections.unmodifiableList(purchasedCars);
    }

    public long getTotalSpent() {
        long total = 0L;
        for (Car car : purchasedCars) {
            total += car.getPrice();
        }
        return total;
    }

    public boolean isVIP() {
        return getTotalSpent() >= 5_000_000L;
    }

    public Car getMostExpensivePurchase() {
        if (purchasedCars.isEmpty()) {
            return null;
        }
        Car mostExpensive = purchasedCars.get(0);
        for (Car car : purchasedCars) {
            if (car.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = car;
            }
        }
        return mostExpensive;
    }

    public boolean hasBoughtMake(String make) {
        if (make == null || make.isBlank()) {
            return false;
        }
        String requestedMake = make.trim();
        for (Car car : purchasedCars) {
            if (car.getMake().equalsIgnoreCase(requestedMake)) {
                return true;
            }
        }
        return false;
    }

    public int getPurchaseCountByYear(int year) {
        int count = 0;
        for (Car car : purchasedCars) {
            if (car.getYear() == year) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String getUserInfo() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("Name: ").append(displayOrUnavailable(name)).append("\n\n")
                .append("Address: ").append(displayOrUnavailable(address)).append("\n\n")
                .append("Contact Info: ").append(displayOrUnavailable(contactInfo)).append("\n\n")
                .append("VIP Status: ").append(isVIP() ? "Yes" : "No").append("\n\n")
                .append("Total Spent: ").append(getTotalSpent()).append(" Taka\n\n")
                .append("Purchased Cars:\n\n");

        if (purchasedCars.isEmpty()) {
            userInfo.append("No cars purchased yet.\n");
        } else {
            for (Car car : purchasedCars) {
                userInfo.append(car.displayCarInfo()).append("\n\n");
            }
        }
        return userInfo.toString();
    }

    private static String displayOrUnavailable(String value) {
        return value == null || value.isBlank() ? "N/A" : value;
    }
}
