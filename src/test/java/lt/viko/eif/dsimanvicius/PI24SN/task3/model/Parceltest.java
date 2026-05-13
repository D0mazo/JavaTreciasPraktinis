package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Parcel}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class ParcelTest {

    private Parcel parcel;

    /**
     * Creates a fresh {@link Parcel} before each test.
     */
    @BeforeEach
    void setUp() {
        parcel = new Parcel("TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 2.3f, false, 'E');
        parcel.setId(1);
    }

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void testConstructorSetsFields() {
        assertEquals("TRK-001-LT",              parcel.getTrackingNumber());
        assertEquals("Jonas Jonaitis",           parcel.getRecipientName());
        assertEquals("Gedimino pr. 1, Vilnius",  parcel.getDeliveryAddress());
        assertEquals(2.3f, parcel.getTotalWeightKg(), 0.001f);
        assertFalse(parcel.isDelivered());
        assertEquals('E', parcel.getPriorityClass());
    }

    @Test
    @DisplayName("Default constructor creates parcel with empty items list")
    void testDefaultConstructorItemsNotNull() {
        Parcel empty = new Parcel();
        assertNotNull(empty.getItems());
        assertTrue(empty.getItems().isEmpty());
    }

    @Test
    @DisplayName("Setters update all fields correctly")
    void testSetters() {
        parcel.setId(5);
        parcel.setTrackingNumber("TRK-NEW");
        parcel.setRecipientName("Petras");
        parcel.setDeliveryAddress("Laisves al. 10");
        parcel.setTotalWeightKg(3.0f);
        parcel.setDelivered(true);
        parcel.setPriorityClass('S');

        assertEquals(5,               parcel.getId());
        assertEquals("TRK-NEW",       parcel.getTrackingNumber());
        assertEquals("Petras",        parcel.getRecipientName());
        assertEquals("Laisves al. 10", parcel.getDeliveryAddress());
        assertEquals(3.0f, parcel.getTotalWeightKg(), 0.001f);
        assertTrue(parcel.isDelivered());
        assertEquals('S', parcel.getPriorityClass());
    }

    @Test
    @DisplayName("setItems links each item back to this parcel")
    void testSetItemsLinksParcel() {
        PackageItem laptop = new PackageItem("Laptop", 2.1f, true,  'E');
        PackageItem mouse  = new PackageItem("Mouse",  0.2f, false, 'E');
        parcel.setItems(Arrays.asList(laptop, mouse));

        assertEquals(2, parcel.getItems().size());
        parcel.getItems().forEach(item ->
                assertEquals(parcel, item.getParcel()));
    }

    @Test
    @DisplayName("setItems with null clears the list")
    void testSetItemsWithNull() {
        parcel.setItems(null);
        assertNotNull(parcel.getItems());
        assertTrue(parcel.getItems().isEmpty());
    }

    @Test
    @DisplayName("toString contains key field values")
    void testToString() {
        String result = parcel.toString();
        assertTrue(result.contains("TRK-001-LT"));
        assertTrue(result.contains("Jonas Jonaitis"));
        assertTrue(result.contains("false"));
    }

    @Test
    @DisplayName("Delivered parcel sets delivered flag to true")
    void testDeliveredParcel() {
        Parcel delivered = new Parcel("TRK-002-LT", "Petras",
                "Kaunas", 1.0f, true, 'S');
        assertTrue(delivered.isDelivered());
        assertEquals('S', delivered.getPriorityClass());
    }
}