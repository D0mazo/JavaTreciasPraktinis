package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ParcelService}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class Parcelservicetest {

    private ParcelService service;

    /**
     * Creates a fresh service instance (with seed data) before each test.
     */
    @BeforeEach
    void setUp() {
        service = new ParcelService();
    }

    @Test
    @DisplayName("findAll returns seeded parcels")
    void testFindAllReturnsSeedData() {
        List<Parcel> all = service.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("findById returns parcel when id exists")
    void testFindByIdFound() {
        Optional<Parcel> result = service.findById(1);
        assertTrue(result.isPresent());
        assertEquals("TRK-001-LT", result.get().getTrackingNumber());
    }

    @Test
    @DisplayName("findById returns empty when id does not exist")
    void testFindByIdNotFound() {
        Optional<Parcel> result = service.findById(999);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save adds parcel and assigns auto-incremented id")
    void testSaveAssignsId() {
        Parcel newParcel = new Parcel();
        newParcel.setTrackingNumber("TRK-NEW");

        Parcel saved = service.save(newParcel);

        assertEquals(3, saved.getId());
        assertEquals(3, service.findAll().size());
    }

    @Test
    @DisplayName("update replaces existing parcel")
    void testUpdateExistingParcel() {
        Parcel replacement = new Parcel();
        replacement.setTrackingNumber("TRK-UPDATED");

        Optional<Parcel> result = service.update(1, replacement);

        assertTrue(result.isPresent());
        assertEquals("TRK-UPDATED", result.get().getTrackingNumber());
        assertEquals(1, result.get().getId());
    }

    @Test
    @DisplayName("update returns empty when id does not exist")
    void testUpdateNotFound() {
        Optional<Parcel> result = service.update(999, new Parcel());
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("delete removes parcel when id exists")
    void testDeleteExisting() {
        boolean removed = service.delete(1);
        assertTrue(removed);
        assertEquals(1, service.findAll().size());
    }

    @Test
    @DisplayName("delete returns false when id does not exist")
    void testDeleteNotFound() {
        boolean removed = service.delete(999);
        assertFalse(removed);
        assertEquals(2, service.findAll().size());
    }
}