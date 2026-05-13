package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PackageItem}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class PackageItemTest {

    private PackageItem item;

    /**
     * Creates a fresh {@link PackageItem} before each test.
     */
    @BeforeEach
    void setUp() {
        item = new PackageItem("Laptop", 2.1f, true, 'E');
    }

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void testConstructorSetsFields() {
        assertEquals("Laptop", item.getName());
        assertEquals(2.1f,     item.getWeightKg(), 0.001f);
        assertTrue(            item.isFragile());
        assertEquals('E',      item.getCategoryCode());
    }

    @Test
    @DisplayName("Default constructor creates item with default values")
    void testDefaultConstructor() {
        PackageItem empty = new PackageItem();
        assertNull(        empty.getName());
        assertEquals(0.0f, empty.getWeightKg(), 0.001f);
        assertFalse(       empty.isFragile());
        assertEquals('\0', empty.getCategoryCode());
    }

    @Test
    @DisplayName("Setters update all fields correctly")
    void testSetters() {
        item.setId(99);
        item.setName("Tablet");
        item.setWeightKg(0.5f);
        item.setFragile(false);
        item.setCategoryCode('C');

        assertEquals(99,       item.getId());
        assertEquals("Tablet", item.getName());
        assertEquals(0.5f,     item.getWeightKg(), 0.001f);
        assertFalse(           item.isFragile());
        assertEquals('C',      item.getCategoryCode());
    }

    @Test
    @DisplayName("setParcel links item to parcel")
    void testSetParcel() {
        Parcel parcel = new Parcel("TRK-001-LT", "Jonas",
                "Vilnius", 2.0f, false, 'E');
        item.setParcel(parcel);
        assertEquals("TRK-001-LT", item.getParcel().getTrackingNumber());
    }

    @Test
    @DisplayName("toString contains key field values")
    void testToString() {
        String result = item.toString();
        assertTrue(result.contains("Laptop"));
        assertTrue(result.contains("2.1"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("E"));
    }

    @Test
    @DisplayName("Non-fragile item sets fragile to false")
    void testNonFragileItem() {
        PackageItem shirt = new PackageItem("T-Shirt", 0.3f, false, 'C');
        assertFalse(shirt.isFragile());
        assertEquals('C', shirt.getCategoryCode());
    }
}