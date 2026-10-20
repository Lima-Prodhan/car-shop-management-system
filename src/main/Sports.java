package carshop.vehicles;

/** A sports vehicle with performance-related attributes and illustrative estimates. */
public class Sports extends Car {
    private final int horsepower;
    private final int topSpeed;
    private final double acceleration0to60;

    public Sports(String make, String model, int year, int price,
                  int horsepower, int topSpeed, double acceleration0to60) {
        super(make, model, year, price);
        if (horsepower <= 0) {
            throw new IllegalArgumentException("Horsepower must be positive.");
        }
        if (topSpeed <= 0) {
            throw new IllegalArgumentException("Top speed must be positive.");
        }
        if (!Double.isFinite(acceleration0to60) || acceleration0to60 < 0.0) {
            throw new IllegalArgumentException("0-to-60 time must be a finite, non-negative number.");
        }
        this.horsepower = horsepower;
        this.topSpeed = topSpeed;
        this.acceleration0to60 = acceleration0to60;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public double getAcceleration0to60() {
        return acceleration0to60;
    }

    public boolean isHyperCar() {
        return topSpeed >= 400 && acceleration0to60 <= 3.0;
    }

    /**
     * Produces the project's simplified quarter-mile heuristic.
     * It is for demonstrating calculations, not a physics-based vehicle prediction.
     */
    public double calculateQuarterMileTime() {
        return 14.0 / (horsepower / 200.0) + acceleration0to60;
    }

    public boolean isTrackReady() {
        return horsepower > 300 && topSpeed > 250;
    }

    /** Returns an illustrative annual maintenance estimate in unspecified model units. */
    public double estimateAnnualMaintenanceCost(int milesDriven) {
        if (milesDriven < 0) {
            throw new IllegalArgumentException("Miles driven cannot be negative.");
        }
        double baseCost = 1000.0;
        double costPerMile = 0.5;
        if (horsepower > 500) {
            baseCost *= 1.5;
            costPerMile = 1.0;
        }
        return baseCost + (milesDriven * costPerMile);
    }

    /** A missing opponent cannot be declared a loser. */
    public boolean beatsInDragRace(Sports opponent) {
        return opponent != null && calculateQuarterMileTime() < opponent.calculateQuarterMileTime();
    }

    @Override
    public String displayCarInfo() {
        return "Make: " + getMake() + " | " +
                "Model: " + getModel() + " | " +
                "Year: " + getYear() + " | " +
                "Price: " + getPrice() + " Taka | " +
                "Horsepower: " + getHorsepower() + " HP | " +
                "Top Speed: " + getTopSpeed() + " km/h | " +
                "0-60 mph Acceleration Time: " + getAcceleration0to60() + " seconds | " +
                "Is Hypercar: " + (isHyperCar() ? "Yes" : "No");
    }
}
