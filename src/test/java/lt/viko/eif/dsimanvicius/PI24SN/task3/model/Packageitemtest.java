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
        item = new PackageItem(1, "Laptop", 2.1f, true, 'E');
    }

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void testConstructorSetsFields() {
        assertEquals(1,        item.getId());
        assertEquals("Laptop", item.getName());
        assertEquals(2.1f,     item.getWeightKg(), 0.001f);
        assertTrue(            item.isFragile());
        assertEquals('E',      item.getCategoryCode());
    }

    @Test
    @DisplayName("Default constructor creates item with default values")
    void testDefaultConstructor() {
        PackageItem empty = new PackageItem();
        assertEquals(0,    empty.getId());
        assertNull(        empty.getName());
        assertEquals(0.0f, empty.getWeightKg(), 0.001f);
        assertFalse(       empty.isFragile());
        assertEquals('\0', empty.getCategoryCode());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        item.setId(99);
        item.setName("Tablet");
        item.setWeightKg(0.5f);
        item.setFragile(false);
        item.setCategoryCode('C');

        assertEquals(99,      item.getId());
        assertEquals("Tablet", item.getName());
        assertEquals(0.5f,    item.getWeightKg(), 0.001f);
        assertFalse(          item.isFragile());
        assertEquals('C',     item.getCategoryCode());
    }

    @Test
    @DisplayName("toString contains key field values")
    void testToString() {
        String result = item.toString();
        assertTrue(result.contains("Laptop"));
        assertTrue(result.contains("2.1"));
        assertTrue(result.contains("true"));
    }
}