package lt.viko.eif.dsimanvicius.PI24SN.task3.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JAX-RS resource that serves the generated {@code openapi.json} file
 * directly from the running application.
 *
 * <p>This allows Swagger UI and APIMATIC to load the spec via URL:
 * {@code http://localhost:8080/api/openapi.json}</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
@Path("/openapi.json")
@Produces(MediaType.APPLICATION_JSON)
public class OpenApiResource {

    /**
     * Serves the generated OpenAPI JSON specification.
     *
     * <p>HTTP {@code GET /api/openapi.json}</p>
     *
     * @return {@code 200 OK} with the OpenAPI JSON content,
     *         or {@code 404} if the file has not been generated yet
     */
    @GET
    public Response getOpenApiSpec() {
        try {
            String content = Files.readString(Paths.get("test-files", "openapi.json"));
            return Response.ok(content).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"openapi.json not found. Start the application first.\"}")
                    .build();
        }
    }
}