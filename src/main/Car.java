package carshop.vehicles;

import java.util.Objects;

/**
 * Shared state and calculations for every vehicle in the car shop.
 * The financial formulas in this academic example are illustrative, not market quotes.
 */
public abstract class Car {
    private final String make;
    private final String model;
    private final int year;
    private final int price;

    protected Car(String make, String model, int year, int price) {
        this.make = requireText(make, "Make");
        this.model = requireText(model, "Model");
        if (year <= 0) {
            throw new IllegalArgumentException("Manufacturing year must be positive.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.year = year;
        this.price = price;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    /**
     * Estimates a vehicle's value after annual depreciation.
     * The rate must be finite and between 0 (inclusive) and 1 (inclusive).
     */
    public double calculateDepreciation(int currentYear, double ratePerYear) {
        if (currentYear < year) {
            throw new IllegalArgumentException("Current year cannot be earlier than the manufacturing year.");
        }
        if (!Double.isFinite(ratePerYear) || ratePerYear < 0.0 || ratePerYear > 1.0) {
            throw new IllegalArgumentException("Depreciation rate must be finite and between 0 and 1.");
        }
        int yearsOld = currentYear - year;
        return price * Math.pow(1.0 - ratePerYear, yearsOld);
    }

    public boolean isAntique(int currentYear) {
        if (currentYear < year) {
            throw new IllegalArgumentException("Current year cannot be earlier than the manufacturing year.");
        }
        return currentYear - year >= 25;
    }

    /** Returns a simple insurance-cost estimate; this is not an insurer quote. */
    public double estimateInsurance(int driverAge) {
        if (driverAge <= 0) {
            throw new IllegalArgumentException("Driver age must be positive.");
        }
        double estimate = price * 0.05;
        if (driverAge < 25) {
            estimate *= 1.5;
        } else if (driverAge > 65) {
            estimate *= 1.2;
        }
        return estimate;
    }

    public boolean isSameMake(Car otherCar) {
        return otherCar != null && make.equalsIgnoreCase(otherCar.getMake());
    }

    /** Vehicle-specific display description used by the Swing interface. */
    public abstract String displayCarInfo();
}
