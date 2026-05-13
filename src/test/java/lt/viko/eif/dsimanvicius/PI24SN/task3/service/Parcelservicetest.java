package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.PackageItem;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.repository.ParcelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ParcelService} using Mockito to mock the repository.
 *
 * @author dsimanvicius
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private ParcelService parcelService;

    private Parcel parcel1;
    private Parcel parcel2;

    /**
     * Sets up sample parcels before each test.
     */
    @BeforeEach
    void setUp() {
        parcel1 = new Parcel("TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 2.3f, false, 'E');
        parcel1.setId(1);

        parcel2 = new Parcel("TRK-002-LT", "Petras Petraitis",
                "Laisves al. 10, Kaunas", 2.0f, true, 'S');
        parcel2.setId(2);
    }

    @Test
    @DisplayName("findAll returns all parcels from repository")
    void testFindAll() {
        when(parcelRepository.findAll()).thenReturn(Arrays.asList(parcel1, parcel2));
        List<Parcel> result = parcelService.findAll();
        assertEquals(2, result.size());
        verify(parcelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no parcels exist")
    void testFindAllEmpty() {
        when(parcelRepository.findAll()).thenReturn(List.of());
        List<Parcel> result = parcelService.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findById returns parcel when id exists")
    void testFindByIdFound() {
        when(parcelRepository.findById(1)).thenReturn(Optional.of(parcel1));
        Optional<Parcel> result = parcelService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("TRK-001-LT", result.get().getTrackingNumber());
    }

    @Test
    @DisplayName("findById returns empty when id not found")
    void testFindByIdNotFound() {
        when(parcelRepository.findById(999)).thenReturn(Optional.empty());
        Optional<Parcel> result = parcelService.findById(999);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save persists parcel via repository")
    void testSave() {
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel1);
        Parcel saved = parcelService.save(parcel1);
        assertNotNull(saved);
        assertEquals("TRK-001-LT", saved.getTrackingNumber());
        verify(parcelRepository, times(1)).save(parcel1);
    }

    @Test
    @DisplayName("save links items to parcel before saving")
    void testSaveLinksItems() {
        PackageItem item = new PackageItem("Laptop", 2.1f, true, 'E');
        parcel1.getItems().add(item);
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel1);

        parcelService.save(parcel1);

        assertEquals(parcel1, parcel1.getItems().get(0).getParcel());
    }

    @Test
    @DisplayName("update returns updated parcel when id exists")
    void testUpdateFound() {
        when(parcelRepository.existsById(1)).thenReturn(true);
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel1);
        Optional<Parcel> result = parcelService.update(1, parcel1);
        assertTrue(result.isPresent());
        verify(parcelRepository).save(parcel1);
    }

    @Test
    @DisplayName("update sets correct id before saving")
    void testUpdateSetsId() {
        when(parcelRepository.existsById(2)).thenReturn(true);
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel1);
        parcelService.update(2, parcel1);
        assertEquals(2, parcel1.getId());
    }

    @Test
    @DisplayName("update returns empty when id not found")
    void testUpdateNotFound() {
        when(parcelRepository.existsById(999)).thenReturn(false);
        Optional<Parcel> result = parcelService.update(999, parcel1);
        assertFalse(result.isPresent());
        verify(parcelRepository, never()).save(any());
    }

    @Test
    @DisplayName("delete returns true and calls repository when id exists")
    void testDeleteFound() {
        when(parcelRepository.existsById(1)).thenReturn(true);
        boolean result = parcelService.delete(1);
        assertTrue(result);
        verify(parcelRepository).deleteById(1);
    }

    @Test
    @DisplayName("delete returns false when id not found")
    void testDeleteNotFound() {
        when(parcelRepository.existsById(999)).thenReturn(false);
        boolean result = parcelService.delete(999);
        assertFalse(result);
        verify(parcelRepository, never()).deleteById(any());
    }
}