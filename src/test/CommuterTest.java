package carshop.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommuterTest {

    private Commuter commuter;

    @BeforeEach
    public void setUp() {
        // Commuter(String make, String model, int year, int price, double fuelEfficiency, int seatingCapacity, double trunkSpace)
        commuter = new Commuter("Toyota", "Corolla", 2010, 1500000, 18.0, 5, 470.0);
    }

    // --- Inherited Car Methods ---
    @Test
    public void testCalculateDepreciation() {
        // 10 years depreciation at 5%
        double depreciated = commuter.calculateDepreciation(2020, 0.05);
        double expected = 1500000 * Math.pow(0.95, 10);
        assertEquals(expected, depreciated, 0.1, "Depreciation math should be correct.");
    }

    @Test
    public void testIsAntique() {
        assertTrue(commuter.isAntique(2035), "Car should be antique after 25 years.");
        assertFalse(commuter.isAntique(2034), "Car should not be antique before 25 years.");
    }

    @Test
    public void testEstimateInsurance() {
        // Base rate = 1500000 * 0.05 = 75000
        assertEquals(112500, commuter.estimateInsurance(20), 0.1, "Under 25 should have 1.5x multiplier.");
        assertEquals(75000, commuter.estimateInsurance(40), 0.1, "Normal age should have standard rate.");
        assertEquals(90000, commuter.estimateInsurance(70), 0.1, "Over 65 should have 1.2x multiplier.");
    }

    @Test
    public void testIsSameMake() {
        Commuter other = new Commuter("TOYOTA", "Camry", 2022, 3000000, 14.0, 5, 500);
        assertTrue(commuter.isSameMake(other), "Should correctly identify case-insensitive same make.");
    }

    // --- Commuter Specific Methods ---
    @Test
    public void testCalculateTravelCost() {
        // distance: 180km, efficiency: 18 km/l -> 10 liters needed.
        // price: 130 taka/liter -> 1300 taka total.
        assertEquals(1300.0, commuter.calculateTravelCost(180.0, 130.0), 0.01, "Travel cost calculation should be accurate.");
    }

    @Test
    public void testCanFitLuggage() {
        assertTrue(commuter.canFitLuggage(400.0), "Should fit if luggage volume is less than trunk space.");
        assertFalse(commuter.canFitLuggage(500.0), "Should not fit if luggage volume is greater than trunk space.");
    }

    @Test
    public void testIsHighlyEfficient() {
        assertFalse(commuter.isHighlyEfficient(), "18.0 should not be highly efficient (threshold 20.0).");
        
        Commuter efficientCommuter = new Commuter("Honda", "Prius", 2023, 2000000, 25.0, 5, 400);
        assertTrue(efficientCommuter.isHighlyEfficient(), "25.0 should be highly efficient.");
    }


    @Test
    public void testFamilyFriendlyRequiresReasonableLuggageCapacity() {
        Commuter smallBoot = new Commuter("Mini", "Compact", 2022, 1200000, 20.0, 5, 299.0);
        Commuter familyBoot = new Commuter("Toyota", "Family", 2022, 2200000, 16.0, 5, 300.0);
        assertFalse(smallBoot.isFamilyFriendly());
        assertTrue(familyBoot.isFamilyFriendly());
    }

    @Test
    public void testRejectsInvalidVehicleSpecifications() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", "Invalid", 2020, 1000000, 0.0, 5, 400.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", "Invalid", 2020, 1000000, 15.0, 0, 400.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", "Invalid", 2020, 1000000, 15.0, 5, Double.NaN));
    }

    @Test
    public void testTravelCostRejectsNegativeInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> commuter.calculateTravelCost(-1.0, 130.0));
        assertThrows(IllegalArgumentException.class,
                () -> commuter.calculateTravelCost(100.0, -1.0));
    }

    @Test
    public void testLuggageVolumeAtCapacityIsAccepted() {
        assertTrue(commuter.canFitLuggage(470.0));
        assertThrows(IllegalArgumentException.class,
                () -> commuter.canFitLuggage(-0.1));
    }
}
