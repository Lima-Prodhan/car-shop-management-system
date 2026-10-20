package carshop.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import carshop.vehicles.Car;
import carshop.vehicles.Commuter;
import carshop.vehicles.Sports;
import carshop.users.Customer;

public class CarShopTest {

    private CarShop shop;
    private Commuter commuter1;
    private Commuter commuter2;
    private Sports sports1;

    @BeforeEach
    public void setUp() {
        shop = new CarShop();
        commuter1 = new Commuter("Toyota", "Corolla", 2020, 2500000, 15.0, 5, 470.0);
        commuter2 = new Commuter("Honda", "Civic", 2021, 2800000, 14.5, 5, 450.0);
        sports1 = new Sports("Porsche", "911", 2022, 15000000, 450, 310, 3.2);

        shop.addCarToInventory(commuter1);
        shop.addCarToInventory(commuter2);
        shop.addCarToInventory(sports1);
    }

    @Test
    public void testGetTotalInventoryValue() {
        int expectedTotal = 2500000 + 2800000 + 15000000;
        assertEquals(expectedTotal, shop.getTotalInventoryValue(), "Total inventory value should match sum of all cars.");
    }

    @Test
    public void testFindCarsByMake() {
        List<Car> foundCars = shop.findCarsByMake("Honda");
        assertEquals(1, foundCars.size(), "Should find exactly 1 Honda.");
        assertEquals("Honda", foundCars.get(0).getMake(), "The found car make should be Honda.");

        List<Car> noCars = shop.findCarsByMake("Ferrari");
        assertTrue(noCars.isEmpty(), "Should return empty list for make not in inventory.");
    }

    @Test
    public void testGetMostExpensiveCar() {
        Car mostExp = shop.getMostExpensiveCar();
        assertNotNull(mostExp);
        assertEquals("Porsche", mostExp.getMake(), "Porsche should be the most expensive car.");
        assertEquals(15000000, mostExp.getPrice());
    }

    @Test
    public void testGetCheapestCar() {
        Car cheapest = shop.getCheapestCar();
        assertNotNull(cheapest);
        assertEquals("Toyota", cheapest.getMake(), "Toyota should be the cheapest car.");
        assertEquals(2500000, cheapest.getPrice());
    }

    @Test
    public void testRemoveCar() {
        boolean removed = shop.removeCar(commuter1);
        assertTrue(removed, "Car should be successfully removed.");
        assertEquals(2, shop.getInventory().size(), "Inventory size should decrease by 1.");
    }

    @Test
    public void testGetAveragePrice() {
        int expectedTotal = 2500000 + 2800000 + 15000000;
        double expectedAverage = expectedTotal / 3.0;
        assertEquals(expectedAverage, shop.getAveragePrice(), 0.01, "Average price should be total value divided by size.");
    }

    @Test
    public void testEmptyShopOperations() {
        CarShop emptyShop = new CarShop();
        assertEquals(0, emptyShop.getTotalInventoryValue());
        assertEquals(0.0, emptyShop.getAveragePrice(), 0.001);
        assertNull(emptyShop.getMostExpensiveCar());
        assertNull(emptyShop.getCheapestCar());
    }

    // 1. Exception / Negative Testing
    @Test
    public void testNegativeCarPriceThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Commuter("Toyota", "Corolla", 2020, -5000, 15.0, 5, 470.0);
        });
        
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    // 2. Integration Testing
    @Test
    public void testCustomerPurchasingFlow() {
        CarShop localShop = new CarShop();
        Customer customer = new Customer();
        Commuter car = new Commuter("Toyota", "Corolla", 2020, 2500000, 15.0, 5, 470.0);
        
        localShop.addCarToInventory(car);
        
        localShop.removeCar(car);
        customer.updatePurchasedCars(car);
        
        assertEquals(0, localShop.getInventory().size(), "Shop inventory should be empty");
        assertEquals(2500000, customer.getTotalSpent(), "Customer should have spent the car's price");
    }

    // 3. Parameterized Testing
    @ParameterizedTest
    @ValueSource(strings = {"Toyota", "TOYOTA", "toyota", "tOyOtA"})
    public void testFindCarsByMakeIsCaseInsensitive(String searchMake) {
        CarShop localShop = new CarShop();
        localShop.addCarToInventory(new Commuter("Toyota", "Corolla", 2020, 2500000, 15.0, 5, 470.0));
        
        assertEquals(1, localShop.findCarsByMake(searchMake).size(), "Should find the Toyota regardless of case.");
    }

    // 4. Boundary Value Testing
    @Test
    public void testSearchWithEmptyOrNullMake() {
        CarShop localShop = new CarShop();
        localShop.addCarToInventory(new Commuter("Toyota", "Corolla", 2020, 2500000, 15.0, 5, 470.0));
        
        assertTrue(localShop.findCarsByMake("").isEmpty(), "Searching by empty string should return empty list");
        assertTrue(localShop.findCarsByMake(null).isEmpty(), "Searching by null should return empty list");
    }


    @Test
    public void testInventoryViewCannotBeModifiedDirectly() {
        assertThrows(UnsupportedOperationException.class,
                () -> shop.getInventory().clear(),
                "Callers should use inventory methods rather than mutate the internal list.");
    }

    @Test
    public void testAddingNullCarIsRejected() {
        assertThrows(NullPointerException.class,
                () -> shop.addCarToInventory(null));
    }

    @Test
    public void testMakeSearchTrimsWhitespace() {
        assertEquals(1, shop.findCarsByMake("  Toyota  ").size());
        assertTrue(shop.findCarsByMake("   ").isEmpty());
    }


    @Test
    public void testInventoryValueUsesLongAggregation() {
        CarShop largeShop = new CarShop();
        largeShop.addCarToInventory(new Commuter("BrandA", "ModelA", 2020,
                2_000_000_000, 15.0, 5, 400.0));
        largeShop.addCarToInventory(new Commuter("BrandB", "ModelB", 2021,
                2_000_000_000, 15.0, 5, 400.0));
        assertEquals(4_000_000_000L, largeShop.getTotalInventoryValue());
        assertEquals(2_000_000_000.0, largeShop.getAveragePrice(), 0.01);
    }
}
