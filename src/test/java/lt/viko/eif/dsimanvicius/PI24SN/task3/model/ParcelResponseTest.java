package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ParcelResponse}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class ParcelResponseTest {

    private Parcel parcel;
    private ParcelResponse parcelResponse;

    /**
     * Creates a fresh {@link Parcel} and {@link ParcelResponse} before each test.
     */
    @BeforeEach
    void setUp() {
        parcel = new Parcel("TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 2.3f, false, 'E');
        parcel.setId(1);
        parcelResponse = new ParcelResponse(parcel);
    }

    @Test
    @DisplayName("Constructor sets parcel correctly")
    void testConstructorSetsParcel() {
        assertNotNull(parcelResponse.getParcel());
        assertEquals("TRK-001-LT", parcelResponse.getParcel().getTrackingNumber());
    }

    @Test
    @DisplayName("Constructor initialises empty links list")
    void testConstructorInitialisesEmptyLinks() {
        assertNotNull(parcelResponse.getLinks());
        assertTrue(parcelResponse.getLinks().isEmpty());
    }

    @Test
    @DisplayName("Default constructor creates response with null parcel and empty links")
    void testDefaultConstructor() {
        ParcelResponse empty = new ParcelResponse();
        assertNull(empty.getParcel());
        assertNotNull(empty.getLinks());
        assertTrue(empty.getLinks().isEmpty());
    }

    @Test
    @DisplayName("addLink appends link to the list")
    void testAddLink() {
        Link link = new Link("self",
                "http://localhost:8080/api/parcels/1", "GET");
        parcelResponse.addLink(link);

        assertEquals(1, parcelResponse.getLinks().size());
        assertEquals("self", parcelResponse.getLinks().get(0).getRel());
    }

    @Test
    @DisplayName("addLink can add multiple links")
    void testAddMultipleLinks() {
        String base = "http://localhost:8080/api/parcels";
        parcelResponse.addLink(new Link("self",   base + "/1", "GET"));
        parcelResponse.addLink(new Link("update", base + "/1", "PUT"));
        parcelResponse.addLink(new Link("delete", base + "/1", "DELETE"));
        parcelResponse.addLink(new Link("all",    base,        "GET"));

        assertEquals(4, parcelResponse.getLinks().size());
    }

    @Test
    @DisplayName("setLinks replaces the entire links list")
    void testSetLinks() {
        List<Link> newLinks = Arrays.asList(
                new Link("self", "http://localhost:8080/api/parcels/1", "GET")
        );
        parcelResponse.setLinks(newLinks);

        assertEquals(1,      parcelResponse.getLinks().size());
        assertEquals("self", parcelResponse.getLinks().get(0).getRel());
    }

    @Test
    @DisplayName("setParcel replaces the parcel")
    void testSetParcel() {
        Parcel newParcel = new Parcel("TRK-999-LT", "New Person",
                "Kaunas", 1.0f, true, 'S');
        parcelResponse.setParcel(newParcel);

        assertEquals("TRK-999-LT", parcelResponse.getParcel().getTrackingNumber());
    }

    @Test
    @DisplayName("Links contain correct rel values after adding 4 HATEOAS links")
    void testLinksRelValues() {
        String base = "http://localhost:8080/api/parcels";
        parcelResponse.addLink(new Link("self",   base + "/1", "GET"));
        parcelResponse.addLink(new Link("update", base + "/1", "PUT"));
        parcelResponse.addLink(new Link("delete", base + "/1", "DELETE"));
        parcelResponse.addLink(new Link("all",    base,        "GET"));

        assertEquals("self",   parcelResponse.getLinks().get(0).getRel());
        assertEquals("update", parcelResponse.getLinks().get(1).getRel());
        assertEquals("delete", parcelResponse.getLinks().get(2).getRel());
        assertEquals("all",    parcelResponse.getLinks().get(3).getRel());
    }
}