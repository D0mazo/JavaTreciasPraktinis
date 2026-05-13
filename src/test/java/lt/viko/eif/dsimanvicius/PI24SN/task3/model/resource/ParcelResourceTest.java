package lt.viko.eif.dsimanvicius.PI24SN.task3.resource;

import jakarta.ws.rs.core.Response;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.ParcelResponse;
import lt.viko.eif.dsimanvicius.PI24SN.task3.service.ParcelService;
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
 * Unit tests for {@link ParcelResource}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ParcelResourceTest {

    @Mock
    private ParcelService parcelService;

    @InjectMocks
    private ParcelResource parcelResource;

    private Parcel parcel1;
    private Parcel parcel2;

    /**
     * Creates sample parcels before each test.
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
    @DisplayName("GET /parcels returns 200 with list of ParcelResponse")
    void testGetAllParcels() {
        when(parcelService.findAll()).thenReturn(Arrays.asList(parcel1, parcel2));
        Response response = parcelResource.getAllParcels();
        assertEquals(200, response.getStatus());
        List<?> body = (List<?>) response.getEntity();
        assertEquals(2, body.size());
        assertInstanceOf(ParcelResponse.class, body.get(0));
    }

    @Test
    @DisplayName("GET /parcels returns empty list when no parcels exist")
    void testGetAllParcelsEmpty() {
        when(parcelService.findAll()).thenReturn(List.of());
        Response response = parcelResource.getAllParcels();
        assertEquals(200, response.getStatus());
        List<?> body = (List<?>) response.getEntity();
        assertTrue(body.isEmpty());
    }

    @Test
    @DisplayName("GET /parcels each response has 4 HATEOAS links")
    void testGetAllParcelsHateoasLinks() {
        when(parcelService.findAll()).thenReturn(Arrays.asList(parcel1));
        Response response = parcelResource.getAllParcels();
        List<?> body = (List<?>) response.getEntity();
        ParcelResponse pr = (ParcelResponse) body.get(0);
        assertEquals(4, pr.getLinks().size());
    }

    @Test
    @DisplayName("GET /parcels/{id} returns 200 with ParcelResponse when found")
    void testGetParcelByIdFound() {
        when(parcelService.findById(1)).thenReturn(Optional.of(parcel1));
        Response response = parcelResource.getParcelById(1);
        assertEquals(200, response.getStatus());
        assertInstanceOf(ParcelResponse.class, response.getEntity());
        ParcelResponse pr = (ParcelResponse) response.getEntity();
        assertEquals("TRK-001-LT", pr.getParcel().getTrackingNumber());
    }

    @Test
    @DisplayName("GET /parcels/{id} returns 404 when not found")
    void testGetParcelByIdNotFound() {
        when(parcelService.findById(999)).thenReturn(Optional.empty());
        Response response = parcelResource.getParcelById(999);
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("GET /parcels/{id} response has 4 HATEOAS links")
    void testGetParcelByIdHateoasLinks() {
        when(parcelService.findById(1)).thenReturn(Optional.of(parcel1));
        Response response = parcelResource.getParcelById(1);
        ParcelResponse pr = (ParcelResponse) response.getEntity();
        assertEquals(4, pr.getLinks().size());
    }

    @Test
    @DisplayName("POST /parcels returns 201 with saved ParcelResponse")
    void testCreateParcel() {
        when(parcelService.save(any(Parcel.class))).thenReturn(parcel1);
        Response response = parcelResource.createParcel(parcel1);
        assertEquals(201, response.getStatus());
        assertInstanceOf(ParcelResponse.class, response.getEntity());
    }

    @Test
    @DisplayName("PUT /parcels/{id} returns 200 when parcel exists")
    void testUpdateParcelFound() {
        when(parcelService.update(1, parcel1)).thenReturn(Optional.of(parcel1));
        Response response = parcelResource.updateParcel(1, parcel1);
        assertEquals(200, response.getStatus());
        assertInstanceOf(ParcelResponse.class, response.getEntity());
    }

    @Test
    @DisplayName("PUT /parcels/{id} returns 404 when parcel not found")
    void testUpdateParcelNotFound() {
        when(parcelService.update(999, parcel1)).thenReturn(Optional.empty());
        Response response = parcelResource.updateParcel(999, parcel1);
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("DELETE /parcels/{id} returns 204 when parcel exists")
    void testDeleteParcelFound() {
        when(parcelService.delete(1)).thenReturn(true);
        Response response = parcelResource.deleteParcel(1);
        assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("DELETE /parcels/{id} returns 404 when parcel not found")
    void testDeleteParcelNotFound() {
        when(parcelService.delete(999)).thenReturn(false);
        Response response = parcelResource.deleteParcel(999);
        assertEquals(404, response.getStatus());
    }
}