package carshop.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SportsTest {

    private Sports sportsCar;
    private Sports hyperCar;

    @BeforeEach
    public void setUp() {
        // Sports(String make, String model, int year, int price, int horsepower, int topSpeed, double acceleration0to60)
        sportsCar = new Sports("Porsche", "911", 2022, 15000000, 450, 310, 3.2);
        hyperCar = new Sports("Bugatti", "Chiron", 2023, 300000000, 1500, 420, 2.4);
    }

    @Test
    public void testIsHyperCar() {
        assertFalse(sportsCar.isHyperCar(), "Standard sports car should not be a hyper car.");
        assertTrue(hyperCar.isHyperCar(), "Car with 400+ top speed and <= 3.0 accel should be hyper car.");
    }

    @Test
    public void testCalculateQuarterMileTime() {
        // Formula: 14.0 / (hp / 200) + acceleration
        // For Porsche: 14.0 / (450 / 200) + 3.2 = 14.0 / 2.25 + 3.2 = 6.222 + 3.2 = 9.422
        assertEquals(9.42, sportsCar.calculateQuarterMileTime(), 0.1, "Quarter mile time calculation should be accurate.");
    }

    @Test
    public void testIsTrackReady() {
        assertTrue(sportsCar.isTrackReady(), "Car with > 300 hp and > 250 top speed should be track ready.");
        
        Sports slowSports = new Sports("Mazda", "Miata", 2020, 3000000, 181, 220, 5.7);
        assertFalse(slowSports.isTrackReady(), "Car not meeting track thresholds should return false.");
    }

    @Test
    public void testEstimateAnnualMaintenanceCost() {
        // Porsche (450 HP <= 500)
        // baseCost: 1000, costPerMile: 0.5. For 10000 miles: 1000 + 5000 = 6000
        assertEquals(6000.0, sportsCar.estimateAnnualMaintenanceCost(10000), 0.01);

        // Bugatti (1500 HP > 500)
        // baseCost: 1500, costPerMile: 1.0. For 10000 miles: 1500 + 10000 = 11500
        assertEquals(11500.0, hyperCar.estimateAnnualMaintenanceCost(10000), 0.01);
    }

    @Test
    public void testBeatsInDragRace() {
        assertTrue(hyperCar.beatsInDragRace(sportsCar), "Hyper car should beat a standard sports car.");
        assertFalse(sportsCar.beatsInDragRace(hyperCar), "Standard sports car should not beat a hyper car.");
    }


    @Test
    public void testMaintenanceCostRejectsNegativeMileage() {
        assertThrows(IllegalArgumentException.class,
                () -> sportsCar.estimateAnnualMaintenanceCost(-1));
    }

    @Test
    public void testMissingDragRaceOpponentDoesNotCountAsWin() {
        assertFalse(sportsCar.beatsInDragRace(null));
    }

    @Test
    public void testRejectsInvalidPerformanceSpecifications() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sports("Porsche", "Invalid", 2022, 1000000, 0, 310, 3.2));
        assertThrows(IllegalArgumentException.class,
                () -> new Sports("Porsche", "Invalid", 2022, 1000000, 450, -1, 3.2));
        assertThrows(IllegalArgumentException.class,
                () -> new Sports("Porsche", "Invalid", 2022, 1000000, 450, 310, Double.POSITIVE_INFINITY));
    }
}
