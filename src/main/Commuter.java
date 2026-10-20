package carshop.vehicles;

/** A standard commuter vehicle with fuel, seating, and luggage-capacity data. */
public class Commuter extends Car {
    private final double fuelEfficiency;
    private final int seatingCapacity;
    private final double trunkSpace; // litres

    public Commuter(String make, String model, int year, int price,
                    double fuelEfficiency, int seatingCapacity, double trunkSpace) {
        super(make, model, year, price);
        if (!Double.isFinite(fuelEfficiency) || fuelEfficiency <= 0.0) {
            throw new IllegalArgumentException("Fuel efficiency must be a positive finite number.");
        }
        if (seatingCapacity <= 0) {
            throw new IllegalArgumentException("Seating capacity must be positive.");
        }
        if (!Double.isFinite(trunkSpace) || trunkSpace < 0.0) {
            throw new IllegalArgumentException("Trunk space must be a finite, non-negative number of litres.");
        }
        this.fuelEfficiency = fuelEfficiency;
        this.seatingCapacity = seatingCapacity;
        this.trunkSpace = trunkSpace;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public double getTrunkSpace() {
        return trunkSpace;
    }

    public boolean isFamilyFriendly() {
        return seatingCapacity >= 5 && trunkSpace >= 300.0;
    }

    /** Estimates fuel cost from distance, fuel efficiency, and price per litre. */
    public double calculateTravelCost(double distanceKm, double fuelPricePerLiter) {
        requireNonNegativeFinite(distanceKm, "Distance");
        requireNonNegativeFinite(fuelPricePerLiter, "Fuel price per litre");
        double litresNeeded = distanceKm / fuelEfficiency;
        double estimate = litresNeeded * fuelPricePerLiter;
        if (!Double.isFinite(estimate)) {
            throw new IllegalArgumentException("Trip-cost estimate exceeds the supported numeric range.");
        }
        return estimate;
    }

    public boolean canFitLuggage(double totalLuggageVolume) {
        requireNonNegativeFinite(totalLuggageVolume, "Luggage volume");
        return totalLuggageVolume <= trunkSpace;
    }

    public boolean isHighlyEfficient() {
        return fuelEfficiency >= 20.0;
    }

    /** Returns seating capacity; the model treats the value as including the driver. */
    public int maxPassengersIncludingDriver() {
        return seatingCapacity;
    }

    private static void requireNonNegativeFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(fieldName + " must be a finite, non-negative number.");
        }
    }

    @Override
    public String displayCarInfo() {
        return "Make: " + getMake() + " | " +
                "Model: " + getModel() + " | " +
                "Year: " + getYear() + " | " +
                "Price: " + getPrice() + " Taka | " +
                "Fuel Efficiency: " + getFuelEfficiency() + " KMPL | " +
                "Seating Capacity: " + getSeatingCapacity() + " persons | " +
                "Trunk Space: " + getTrunkSpace() + " litres | " +
                "Family Friendly: " + (isFamilyFriendly() ? "Yes" : "No");
    }
}
