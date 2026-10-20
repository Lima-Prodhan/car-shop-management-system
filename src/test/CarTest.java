package carshop.vehicles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Commuter sampleCar() {
        return new Commuter(" Toyota ", " Corolla ", 2010, 1_500_000, 18.0, 5, 470.0);
    }

    @Test
    void constructorTrimsTextAndRejectsInvalidCoreFields() {
        Commuter car = sampleCar();
        assertEquals("Toyota", car.getMake());
        assertEquals("Corolla", car.getModel());

        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("   ", "Corolla", 2010, 1000, 18.0, 5, 470.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", null, 2010, 1000, 18.0, 5, 470.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", "Corolla", 0, 1000, 18.0, 5, 470.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Commuter("Toyota", "Corolla", 2010, -1, 18.0, 5, 470.0));
    }

    @Test
    void depreciationRejectsInvalidYearAndRates() {
        Commuter car = sampleCar();
        assertThrows(IllegalArgumentException.class,
                () -> car.calculateDepreciation(2009, 0.05));
        assertThrows(IllegalArgumentException.class,
                () -> car.calculateDepreciation(2020, -0.01));
        assertThrows(IllegalArgumentException.class,
                () -> car.calculateDepreciation(2020, 1.01));
        assertThrows(IllegalArgumentException.class,
                () -> car.calculateDepreciation(2020, Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> car.calculateDepreciation(2020, Double.POSITIVE_INFINITY));
    }

    @Test
    void depreciationRateBoundariesAreAccepted() {
        Commuter car = sampleCar();
        assertEquals(1_500_000.0, car.calculateDepreciation(2010, 0.0), 0.001);
        assertEquals(0.0, car.calculateDepreciation(2011, 1.0), 0.001);
    }

    @Test
    void antiqueCheckRejectsYearBeforeManufacture() {
        Commuter car = sampleCar();
        assertFalse(car.isAntique(2034));
        assertTrue(car.isAntique(2035));
        assertThrows(IllegalArgumentException.class, () -> car.isAntique(2009));
    }

    @Test
    void insuranceEstimateRejectsNonPositiveAge() {
        Commuter car = sampleCar();
        assertThrows(IllegalArgumentException.class, () -> car.estimateInsurance(0));
        assertThrows(IllegalArgumentException.class, () -> car.estimateInsurance(-1));
    }

    @Test
    void sameMakeComparisonHandlesNullAndCaseDifferences() {
        Commuter car = sampleCar();
        Commuter sameMake = new Commuter("TOYOTA", "Camry", 2022, 3_000_000, 14.0, 5, 500.0);
        Commuter otherMake = new Commuter("Honda", "Civic", 2022, 2_000_000, 14.0, 5, 450.0);
        assertTrue(car.isSameMake(sameMake));
        assertFalse(car.isSameMake(otherMake));
        assertFalse(car.isSameMake(null));
    }
}
