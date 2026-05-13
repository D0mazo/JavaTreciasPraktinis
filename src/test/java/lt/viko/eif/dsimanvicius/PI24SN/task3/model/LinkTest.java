package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Link}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class LinkTest {

    private Link link;

    /**
     * Creates a fresh {@link Link} before each test.
     */
    @BeforeEach
    void setUp() {
        link = new Link("self", "http://localhost:8080/api/parcels/1", "GET");
    }

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void testConstructorSetsFields() {
        assertEquals("self",                                  link.getRel());
        assertEquals("http://localhost:8080/api/parcels/1",  link.getHref());
        assertEquals("GET",                                   link.getMethod());
    }

    @Test
    @DisplayName("Default constructor creates link with null fields")
    void testDefaultConstructor() {
        Link empty = new Link();
        assertNull(empty.getRel());
        assertNull(empty.getHref());
        assertNull(empty.getMethod());
    }

    @Test
    @DisplayName("Setters update all fields correctly")
    void testSetters() {
        link.setRel("update");
        link.setHref("http://localhost:8080/api/parcels/2");
        link.setMethod("PUT");

        assertEquals("update",                               link.getRel());
        assertEquals("http://localhost:8080/api/parcels/2", link.getHref());
        assertEquals("PUT",                                  link.getMethod());
    }

    @Test
    @DisplayName("Link with DELETE method is set correctly")
    void testDeleteLink() {
        Link deleteLink = new Link("delete",
                "http://localhost:8080/api/parcels/1", "DELETE");
        assertEquals("delete", deleteLink.getRel());
        assertEquals("DELETE", deleteLink.getMethod());
    }

    @Test
    @DisplayName("Link with POST method is set correctly")
    void testPostLink() {
        Link postLink = new Link("create",
                "http://localhost:8080/api/parcels", "POST");
        assertEquals("create", postLink.getRel());
        assertEquals("POST",   postLink.getMethod());
    }
}