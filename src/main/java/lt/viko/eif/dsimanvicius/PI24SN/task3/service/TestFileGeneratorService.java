package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service that automatically generates test and API description files
 * on application startup.
 *
 * <p>Generated files are written to the {@code test-files/} directory:</p>
 * <ul>
 *   <li>{@code soapui-project.xml}       — SoapUI REST project</li>
 *   <li>{@code postman-collection.json}  — Postman collection v2.1</li>
 *   <li>{@code openapi.json}             — OpenAPI 3.0 for APIMATIC</li>
 *   <li>{@code swagger-ui.html}          — Standalone Swagger UI page</li>
 * </ul>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Service
public class TestFileGeneratorService {

    /** Base URL of the running API. */
    private static final String BASE_URL = "http://localhost:8080/api";

    /** Output directory for all generated files. */
    private static final String OUTPUT_DIR = "test-files";

    /** Jackson mapper for building JSON structures. */
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Generates all four test/documentation files.
     * Called automatically by {@link lt.viko.eif.dsimanvicius.PI24SN.task3.config.DataSeeder}.
     */
    public void generateAll() {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            generateSoapUiProject();
            generatePostmanCollection();
            generateOpenApi();
            generateSwaggerUi();
            System.out.println("✅ Test files generated in '" + OUTPUT_DIR + "/' directory:");
            System.out.println("   - soapui-project.xml");
            System.out.println("   - postman-collection.json");
            System.out.println("   - openapi.json");
            System.out.println("   - swagger-ui.html");
        } catch (IOException e) {
            System.err.println("❌ Failed to generate test files: " + e.getMessage());
        }
    }

    /**
     * Generates a SoapUI REST project XML file with all 5 CRUD endpoints
     * and sample request bodies.
     *
     * @throws IOException if the file cannot be written
     */
    private void generateSoapUiProject() throws IOException {
        String sampleBody = """
                {
                  "trackingNumber": "TRK-TEST-001",
                  "recipientName": "Test User",
                  "deliveryAddress": "Gedimino pr. 1, Vilnius",
                  "totalWeightKg": 1.5,
                  "delivered": false,
                  "priorityClass": "E",
                  "items": [
                    {
                      "name": "Laptop",
                      "weightKg": 1.2,
                      "fragile": true,
                      "categoryCode": "E"
                    }
                  ]
                }""";

        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <con:soapui-project
                    xmlns:con="http://eviware.com/soapui/config"
                    name="Parcel Logistics API"
                    soapui-version="5.7.0">

                  <con:interface name="Parcel API" type="rest"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:type="con:RestService"
                      basePath="/api">

                    <con:endpoints>
                      <con:endpoint>http://localhost:8080</con:endpoint>
                    </con:endpoints>

                    <con:resource name="parcels" path="/parcels">

                      <con:method name="GET all parcels" method="GET">
                        <con:request name="Request 1">
                          <con:endpoint>http://localhost:8080</con:endpoint>
                        </con:request>
                      </con:method>

                      <con:method name="POST create parcel" method="POST">
                        <con:request name="Request 1">
                          <con:endpoint>http://localhost:8080</con:endpoint>
                          <con:mediaType>application/json</con:mediaType>
                          <con:requestContent><![CDATA[%s]]></con:requestContent>
                        </con:request>
                      </con:method>

                      <con:resource name="{id}" path="/{id}">

                        <con:method name="GET parcel by id" method="GET">
                          <con:request name="Request 1">
                            <con:endpoint>http://localhost:8080</con:endpoint>
                          </con:request>
                        </con:method>

                        <con:method name="PUT update parcel" method="PUT">
                          <con:request name="Request 1">
                            <con:endpoint>http://localhost:8080</con:endpoint>
                            <con:mediaType>application/json</con:mediaType>
                            <con:requestContent><![CDATA[%s]]></con:requestContent>
                          </con:request>
                        </con:method>

                        <con:method name="DELETE parcel" method="DELETE">
                          <con:request name="Request 1">
                            <con:endpoint>http://localhost:8080</con:endpoint>
                          </con:request>
                        </con:method>

                      </con:resource>
                    </con:resource>
                  </con:interface>
                </con:soapui-project>
                """.formatted(sampleBody, sampleBody);

        Files.writeString(Paths.get(OUTPUT_DIR, "soapui-project.xml"), xml);
    }

    /**
     * Generates a Postman Collection v2.1 JSON file with all 5 CRUD endpoints,
     * pre-filled request bodies, and example responses.
     *
     * @throws IOException if the file cannot be written
     */
    private void generatePostmanCollection() throws IOException {
        ObjectNode root = mapper.createObjectNode();

        // Info
        ObjectNode info = root.putObject("info");
        info.put("name", "Parcel Logistics API");
        info.put("description", "JAX-RS REST API for managing logistics parcels with HATEOAS");
        info.put("schema", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        ArrayNode items = root.putArray("item");

        // GET all
        addPostmanRequest(items, "GET all parcels",   "GET",    BASE_URL + "/parcels", null);
        // GET by id
        addPostmanRequest(items, "GET parcel by id",  "GET",    BASE_URL + "/parcels/1", null);
        // POST
        addPostmanRequest(items, "POST create parcel","POST",   BASE_URL + "/parcels", sampleParcelJson());
        // PUT
        addPostmanRequest(items, "PUT update parcel", "PUT",    BASE_URL + "/parcels/1", sampleParcelJson());
        // DELETE
        addPostmanRequest(items, "DELETE parcel",     "DELETE", BASE_URL + "/parcels/2", null);
        // 404
        addPostmanRequest(items, "GET 404 not found", "GET",    BASE_URL + "/parcels/999", null);

        mapper.writeValue(Paths.get(OUTPUT_DIR, "postman-collection.json").toFile(), root);
    }

    /**
     * Adds a single request item to a Postman collection array.
     *
     * @param items  the Postman items array to append to
     * @param name   human-readable request name
     * @param method HTTP method
     * @param url    full request URL
     * @param body   JSON body string, or {@code null} for requests without a body
     */
    private void addPostmanRequest(ArrayNode items, String name,
                                   String method, String url, String body) {
        ObjectNode item    = items.addObject();
        item.put("name", name);

        ObjectNode request = item.putObject("request");
        request.put("method", method);

        ArrayNode headers  = request.putArray("header");
        ObjectNode header  = headers.addObject();
        header.put("key",   "Content-Type");
        header.put("value", "application/json");

        ObjectNode urlNode = request.putObject("url");
        urlNode.put("raw", url);

        if (body != null) {
            ObjectNode bodyNode = request.putObject("body");
            bodyNode.put("mode", "raw");
            bodyNode.put("raw", body);
            ObjectNode options = bodyNode.putObject("options");
            options.putObject("raw").put("language", "json");
        }
    }

    /**
     * Generates an OpenAPI 3.0 JSON file describing the full API,
     * suitable for import into APIMATIC or any OpenAPI-compatible tool.
     *
     * @throws IOException if the file cannot be written
     */
    private void generateOpenApi() throws IOException {
        ObjectNode root = mapper.createObjectNode();
        root.put("openapi", "3.0.0");

        ObjectNode info = root.putObject("info");
        info.put("title",       "Parcel Logistics API");
        info.put("description", "JAX-RS RESTful Web Service for managing logistics parcels with HATEOAS");
        info.put("version",     "1.0.0");

        ArrayNode servers = root.putArray("servers");
        ObjectNode server = servers.addObject();
        server.put("url",         BASE_URL);
        server.put("description", "Local development server");

        ObjectNode paths = root.putObject("paths");

        // /parcels
        ObjectNode parcelsPath = paths.putObject("/parcels");
        addOpenApiGet(parcelsPath,  "getAllParcels",  "Get all parcels",  false);
        addOpenApiPost(parcelsPath, "createParcel",   "Create a new parcel");

        // /parcels/{id}
        ObjectNode parcelIdPath = paths.putObject("/parcels/{id}");
        addOpenApiGet(parcelIdPath,    "getParcelById", "Get parcel by ID",     true);
        addOpenApiPut(parcelIdPath,    "updateParcel",  "Update existing parcel");
        addOpenApiDelete(parcelIdPath, "deleteParcel",  "Delete parcel by ID");

        // Components / schemas
        ObjectNode components = root.putObject("components");
        ObjectNode schemas    = components.putObject("schemas");
        addPackageItemSchema(schemas);
        addParcelSchema(schemas);
        addLinkSchema(schemas);
        addParcelResponseSchema(schemas);

        mapper.writeValue(Paths.get(OUTPUT_DIR, "openapi.json").toFile(), root);
    }

    /**
     * Adds a GET operation node to an OpenAPI path object.
     *
     * @param pathNode  the path object to add the operation to
     * @param operationId unique operation identifier
     * @param summary   short description
     * @param withId    {@code true} if the path has an {@code {id}} parameter
     */
    private void addOpenApiGet(ObjectNode pathNode, String operationId,
                               String summary, boolean withId) {
        ObjectNode op = pathNode.putObject("get");
        op.put("summary", summary);
        op.put("operationId", operationId);
        if (withId) addIdParameter(op);
        ObjectNode responses = op.putObject("responses");
        ObjectNode r200 = responses.putObject("200");
        r200.put("description", "Successful response");
        if (withId) {
            responses.putObject("404").put("description", "Parcel not found");
        }
    }

    /**
     * Adds a POST operation node to an OpenAPI path object.
     *
     * @param pathNode    the path object to add the operation to
     * @param operationId unique operation identifier
     * @param summary     short description
     */
    private void addOpenApiPost(ObjectNode pathNode, String operationId, String summary) {
        ObjectNode op = pathNode.putObject("post");
        op.put("summary", summary);
        op.put("operationId", operationId);
        addRequestBody(op);
        ObjectNode responses = op.putObject("responses");
        responses.putObject("201").put("description", "Parcel created");
    }

    /**
     * Adds a PUT operation node to an OpenAPI path object.
     *
     * @param pathNode    the path object to add the operation to
     * @param operationId unique operation identifier
     * @param summary     short description
     */
    private void addOpenApiPut(ObjectNode pathNode, String operationId, String summary) {
        ObjectNode op = pathNode.putObject("put");
        op.put("summary", summary);
        op.put("operationId", operationId);
        addIdParameter(op);
        addRequestBody(op);
        ObjectNode responses = op.putObject("responses");
        responses.putObject("200").put("description", "Parcel updated");
        responses.putObject("404").put("description", "Parcel not found");
    }

    /**
     * Adds a DELETE operation node to an OpenAPI path object.
     *
     * @param pathNode    the path object to add the operation to
     * @param operationId unique operation identifier
     * @param summary     short description
     */
    private void addOpenApiDelete(ObjectNode pathNode, String operationId, String summary) {
        ObjectNode op = pathNode.putObject("delete");
        op.put("summary", summary);
        op.put("operationId", operationId);
        addIdParameter(op);
        ObjectNode responses = op.putObject("responses");
        responses.putObject("204").put("description", "Parcel deleted");
        responses.putObject("404").put("description", "Parcel not found");
    }

    /**
     * Appends the {@code id} path parameter definition to an operation node.
     *
     * @param op the operation node to append to
     */
    private void addIdParameter(ObjectNode op) {
        ArrayNode params = op.putArray("parameters");
        ObjectNode param = params.addObject();
        param.put("name", "id");
        param.put("in", "path");
        param.put("required", true);
        param.putObject("schema").put("type", "integer");
    }

    /**
     * Appends a JSON request body definition referencing the Parcel schema.
     *
     * @param op the operation node to append to
     */
    private void addRequestBody(ObjectNode op) {
        ObjectNode body    = op.putObject("requestBody");
        body.put("required", true);
        ObjectNode content = body.putObject("content");
        ObjectNode json    = content.putObject("application/json");
        json.putObject("schema").put("$ref", "#/components/schemas/Parcel");
    }

    /**
     * Adds the {@code PackageItem} schema to the components/schemas section.
     *
     * @param schemas the schemas object to add to
     */
    private void addPackageItemSchema(ObjectNode schemas) {
        ObjectNode schema = schemas.putObject("PackageItem");
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("id").put("type", "integer");
        props.putObject("name").put("type", "string");
        props.putObject("weightKg").put("type", "number").put("format", "float");
        props.putObject("fragile").put("type", "boolean");
        props.putObject("categoryCode").put("type", "string");
    }

    /**
     * Adds the {@code Parcel} schema to the components/schemas section.
     *
     * @param schemas the schemas object to add to
     */
    private void addParcelSchema(ObjectNode schemas) {
        ObjectNode schema = schemas.putObject("Parcel");
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("id").put("type", "integer");
        props.putObject("trackingNumber").put("type", "string");
        props.putObject("recipientName").put("type", "string");
        props.putObject("deliveryAddress").put("type", "string");
        props.putObject("totalWeightKg").put("type", "number").put("format", "float");
        props.putObject("delivered").put("type", "boolean");
        props.putObject("priorityClass").put("type", "string");
        ObjectNode items = props.putObject("items");
        items.put("type", "array");
        items.putObject("items").put("$ref", "#/components/schemas/PackageItem");
    }

    /**
     * Adds the {@code Link} schema to the components/schemas section.
     *
     * @param schemas the schemas object to add to
     */
    private void addLinkSchema(ObjectNode schemas) {
        ObjectNode schema = schemas.putObject("Link");
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("rel").put("type", "string");
        props.putObject("href").put("type", "string");
        props.putObject("method").put("type", "string");
    }

    /**
     * Adds the {@code ParcelResponse} HATEOAS wrapper schema.
     *
     * @param schemas the schemas object to add to
     */
    private void addParcelResponseSchema(ObjectNode schemas) {
        ObjectNode schema = schemas.putObject("ParcelResponse");
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("parcel").put("$ref", "#/components/schemas/Parcel");
        ObjectNode links = props.putObject("links");
        links.put("type", "array");
        links.putObject("items").put("$ref", "#/components/schemas/Link");
    }

    /**
     * Generates a standalone Swagger UI HTML page that loads the generated
     * {@code openapi.json} and allows interactive API exploration directly
     * in the browser — no internet connection required for the UI itself.
     *
     * <p>Open {@code test-files/swagger-ui.html} in a browser while the
     * Spring Boot application is running to explore and test all endpoints.</p>
     *
     * @throws IOException if the file cannot be written
     */
    private void generateSwaggerUi() throws IOException {
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Parcel Logistics API — Swagger UI</title>
                  <link rel="stylesheet"
                        href="https://unpkg.com/swagger-ui-dist@5/swagger-ui.css"/>
                </head>
                <body>
                  <div id="swagger-ui"></div>
                  <script src="https://unpkg.com/swagger-ui-dist@5/swagger-ui-bundle.js"></script>
                  <script src="https://unpkg.com/swagger-ui-dist@5/swagger-ui-standalone-preset.js"></script>
                  <script>
                    window.onload = function () {
                      SwaggerUIBundle({
                        url: "http://localhost:8080/api/openapi.json",
                        dom_id: '#swagger-ui',
                        presets: [
                          SwaggerUIBundle.presets.apis,
                          SwaggerUIStandalonePreset
                        ],
                        layout: "StandaloneLayout",
                        deepLinking: true,
                        defaultModelsExpandDepth: 2,
                        defaultModelExpandDepth: 2
                      });
                    };
                  </script>
                </body>
                </html>
                """;
        Files.writeString(Paths.get(OUTPUT_DIR, "swagger-ui.html"), html);
    }

    /**
     * Returns a sample parcel JSON string used in POST and PUT request bodies.
     *
     * @return formatted JSON string
     */
    private String sampleParcelJson() {
        return """
                {
                  "trackingNumber": "TRK-TEST-001",
                  "recipientName": "Test User",
                  "deliveryAddress": "Gedimino pr. 1, Vilnius",
                  "totalWeightKg": 1.5,
                  "delivered": false,
                  "priorityClass": "E",
                  "items": [
                    {
                      "name": "Laptop",
                      "weightKg": 1.2,
                      "fragile": true,
                      "categoryCode": "E"
                    }
                  ]
                }""";
    }
}