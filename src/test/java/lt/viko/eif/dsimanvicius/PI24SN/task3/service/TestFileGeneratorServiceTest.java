package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TestFileGeneratorService}.
 *
 * @author dsimanvicius
 * @version 1.0
 */
class TestFileGeneratorServiceTest {

    private final TestFileGeneratorService service = new TestFileGeneratorService();

    /**
     * Deletes the generated test-files directory after each test.
     *
     * @throws IOException if cleanup fails
     */
    @AfterEach
    void cleanUp() throws IOException {
        var dir = Paths.get("test-files");
        if (Files.exists(dir)) {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> p.toFile().delete());
        }
    }

    @Test
    @DisplayName("generateAll creates test-files directory")
    void testDirectoryCreated() {
        service.generateAll();
        assertTrue(Files.exists(Paths.get("test-files")));
    }

    @Test
    @DisplayName("generateAll creates soapui-project.xml")
    void testSoapUiFileCreated() {
        service.generateAll();
        assertTrue(Files.exists(Paths.get("test-files", "soapui-project.xml")));
    }

    @Test
    @DisplayName("soapui-project.xml contains all 5 HTTP methods")
    void testSoapUiContent() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "soapui-project.xml"));
        assertTrue(content.contains("GET all parcels"));
        assertTrue(content.contains("POST create parcel"));
        assertTrue(content.contains("GET parcel by id"));
        assertTrue(content.contains("PUT update parcel"));
        assertTrue(content.contains("DELETE parcel"));
    }

    @Test
    @DisplayName("generateAll creates postman-collection.json")
    void testPostmanFileCreated() {
        service.generateAll();
        assertTrue(Files.exists(Paths.get("test-files", "postman-collection.json")));
    }

    @Test
    @DisplayName("postman-collection.json contains all 6 requests")
    void testPostmanContent() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "postman-collection.json"));
        assertTrue(content.contains("GET all parcels"));
        assertTrue(content.contains("POST create parcel"));
        assertTrue(content.contains("PUT update parcel"));
        assertTrue(content.contains("DELETE parcel"));
        assertTrue(content.contains("GET 404 not found"));
    }

    @Test
    @DisplayName("postman-collection.json has correct schema version")
    void testPostmanSchema() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "postman-collection.json"));
        assertTrue(content.contains("schema.getpostman.com"));
        assertTrue(content.contains("v2.1.0"));
    }

    @Test
    @DisplayName("generateAll creates openapi.json")
    void testOpenApiFileCreated() {
        service.generateAll();
        assertTrue(Files.exists(Paths.get("test-files", "openapi.json")));
    }

    @Test
    @DisplayName("openapi.json contains OpenAPI version 3.0.0")
    void testOpenApiVersion() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "openapi.json"));
        assertTrue(content.contains("\"openapi\" : \"3.0.0\""));
    }

    @Test
    @DisplayName("openapi.json contains all paths and schemas")
    void testOpenApiContent() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "openapi.json"));
        assertTrue(content.contains("/parcels"));
        assertTrue(content.contains("/parcels/{id}"));
        assertTrue(content.contains("Parcel"));
        assertTrue(content.contains("PackageItem"));
        assertTrue(content.contains("Link"));
        assertTrue(content.contains("ParcelResponse"));
    }

    @Test
    @DisplayName("generateAll creates swagger-ui.html")
    void testSwaggerUiFileCreated() {
        service.generateAll();
        assertTrue(Files.exists(Paths.get("test-files", "swagger-ui.html")));
    }

    @Test
    @DisplayName("swagger-ui.html contains SwaggerUIBundle and openapi.json reference")
    void testSwaggerUiContent() throws IOException {
        service.generateAll();
        String content = Files.readString(Paths.get("test-files", "swagger-ui.html"));
        assertTrue(content.contains("SwaggerUIBundle"));
        assertTrue(content.contains("openapi.json"));
        assertTrue(content.contains("swagger-ui"));
    }

    @Test
    @DisplayName("generateAll can be called multiple times without error")
    void testGenerateAllIdempotent() {
        assertDoesNotThrow(() -> {
            service.generateAll();
            service.generateAll();
        });
    }
}