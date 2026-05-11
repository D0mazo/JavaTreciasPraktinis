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
    private List<PackageItem> sampleItems;

    /**
     * Creates a fully populated {@link Parcel} before each test.
     */
    @BeforeEach
    void setUp() {
        sampleItems = Arrays.asList(
                new PackageItem(1, "Book", 0.4f, false, 'B'),
                new PackageItem(2, "Pen",  0.05f, false, 'S')
        );
        parcel = new Parcel(1, "TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 0.45f, false, 'S', sampleItems);
    }

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void testConstructorSetsFields() {
        assertEquals(1,                    parcel.getId());
        assertEquals("TRK-001-LT",         parcel.getTrackingNumber());
        assertEquals("Jonas Jonaitis",     parcel.getRecipientName());
        assertEquals("Gedimino pr. 1, Vilnius", parcel.getDeliveryAddress());
        assertEquals(0.45f,                parcel.getTotalWeightKg(), 0.001f);
        assertFalse(                       parcel.isDelivered());
        assertEquals('S',                  parcel.getPriorityClass());
        assertEquals(2,                    parcel.getItems().size());
    }

    @Test
    @DisplayName("Default constructor initialises items to empty list")
    void testDefaultConstructorItemsNotNull() {
        Parcel empty = new Parcel();
        assertNotNull(empty.getItems());
        assertTrue(empty.getItems().isEmpty());
    }

    @Test
    @DisplayName("Constructor with null items initialises empty list")
    void testNullItemsBecomesEmptyList() {
        Parcel p = new Parcel(2, "TRK-002", "X", "Y", 1.0f, false, 'E', null);
        assertNotNull(p.getItems());
        assertTrue(p.getItems().isEmpty());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        parcel.setId(5);
        parcel.setTrackingNumber("TRK-NEW");
        parcel.setRecipientName("Petras");
        parcel.setDeliveryAddress("Laisves al. 10");
        parcel.setTotalWeightKg(3.0f);
        parcel.setDelivered(true);
        parcel.setPriorityClass('E');

        assertEquals(5,              parcel.getId());
        assertEquals("TRK-NEW",     parcel.getTrackingNumber());
        assertEquals("Petras",      parcel.getRecipientName());
        assertEquals("Laisves al. 10", parcel.getDeliveryAddress());
        assertEquals(3.0f,           parcel.getTotalWeightKg(), 0.001f);
        assertTrue(                  parcel.isDelivered());
        assertEquals('E',            parcel.getPriorityClass());
    }

    @Test
    @DisplayName("setItems replaces the list")
    void testSetItems() {
        List<PackageItem> newItems = List.of(new PackageItem(9, "Mug", 0.3f, true, 'H'));
        parcel.setItems(newItems);
        assertEquals(1, parcel.getItems().size());
        assertEquals("Mug", parcel.getItems().get(0).getName());
    }

    @Test
    @DisplayName("toString contains key field values")
    void testToString() {
        String result = parcel.toString();
        assertTrue(result.contains("TRK-001-LT"));
        assertTrue(result.contains("Jonas Jonaitis"));
    }
}