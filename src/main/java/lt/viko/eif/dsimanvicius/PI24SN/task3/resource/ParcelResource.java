package lt.viko.eif.dsimanvicius.PI24SN.task3.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Link;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.ParcelResponse;
import lt.viko.eif.dsimanvicius.PI24SN.task3.service.ParcelService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JAX-RS REST resource exposing CRUD operations for {@link Parcel} entities.
 *
 * <p>All responses are enriched with HATEOAS hypermedia links that guide
 * the client toward available next actions, following REST Level 3 maturity.</p>
 *
 * <table border="1">
 *   <caption>Endpoints</caption>
 *   <tr><th>Method</th><th>Path</th><th>Description</th></tr>
 *   <tr><td>GET</td>   <td>/api/parcels</td>      <td>All parcels</td></tr>
 *   <tr><td>GET</td>   <td>/api/parcels/{id}</td> <td>Parcel by id</td></tr>
 *   <tr><td>POST</td>  <td>/api/parcels</td>      <td>Create parcel</td></tr>
 *   <tr><td>PUT</td>   <td>/api/parcels/{id}</td> <td>Update parcel</td></tr>
 *   <tr><td>DELETE</td><td>/api/parcels/{id}</td> <td>Delete parcel</td></tr>
 * </table>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
@Path("/parcels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParcelResource {

    /** Service layer providing database-backed CRUD operations. */
    private final ParcelService parcelService;

    /** JAX-RS context providing the base URI for building HATEOAS links. */
    @Context
    private UriInfo uriInfo;

    /**
     * Constructs the resource with the injected service.
     *
     * @param parcelService service handling persistence logic
     */
    public ParcelResource(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    /**
     * Retrieves all parcels, each enriched with HATEOAS links.
     *
     * <p>HTTP {@code GET /api/parcels}</p>
     *
     * @return {@code 200 OK} with JSON array of {@link ParcelResponse} objects
     */
    @GET
    public Response getAllParcels() {
        List<ParcelResponse> responses = parcelService.findAll()
                .stream()
                .map(parcel -> buildResponse(parcel))
                .collect(Collectors.toList());
        return Response.ok(responses).build();
    }

    /**
     * Retrieves a single parcel by id with HATEOAS links.
     *
     * <p>HTTP {@code GET /api/parcels/{id}}</p>
     *
     * @param id parcel identifier
     * @return {@code 200 OK} with {@link ParcelResponse}, or {@code 404 Not Found}
     */
    @GET
    @Path("/{id}")
    public Response getParcelById(@PathParam("id") int id) {
        Optional<Parcel> result = parcelService.findById(id);
        if (result.isPresent()) {
            return Response.ok(buildResponse(result.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Parcel with id " + id + " not found\"}")
                .build();
    }

    /**
     * Creates and persists a new parcel, returning it with HATEOAS links.
     *
     * <p>HTTP {@code POST /api/parcels}</p>
     *
     * @param parcel parcel data from request body
     * @return {@code 201 Created} with the saved {@link ParcelResponse}
     */
    @POST
    public Response createParcel(Parcel parcel) {
        Parcel saved = parcelService.save(parcel);
        return Response.status(Response.Status.CREATED)
                .entity(buildResponse(saved))
                .build();
    }

    /**
     * Replaces an existing parcel, returning the updated version with HATEOAS links.
     *
     * <p>HTTP {@code PUT /api/parcels/{id}}</p>
     *
     * @param id     id of the parcel to replace
     * @param parcel replacement data from request body
     * @return {@code 200 OK} with updated {@link ParcelResponse}, or {@code 404}
     */
    @PUT
    @Path("/{id}")
    public Response updateParcel(@PathParam("id") int id, Parcel parcel) {
        Optional<Parcel> updated = parcelService.update(id, parcel);
        if (updated.isPresent()) {
            return Response.ok(buildResponse(updated.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Parcel with id " + id + " not found\"}")
                .build();
    }

    /**
     * Deletes a parcel and all its items.
     *
     * <p>HTTP {@code DELETE /api/parcels/{id}}</p>
     *
     * @param id id of the parcel to delete
     * @return {@code 204 No Content} on success, or {@code 404 Not Found}
     */
    @DELETE
    @Path("/{id}")
    public Response deleteParcel(@PathParam("id") int id) {
        boolean removed = parcelService.delete(id);
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Parcel with id " + id + " not found\"}")
                .build();
    }

    /**
     * Wraps a {@link Parcel} in a {@link ParcelResponse} and attaches
     * four standard HATEOAS links: self, update, delete, and all.
     *
     * @param parcel the parcel to wrap
     * @return response object with hypermedia links attached
     */
    private ParcelResponse buildResponse(Parcel parcel) {
        String base = getBaseUrl();
        String self = base + "/" + parcel.getId();

        ParcelResponse response = new ParcelResponse(parcel);
        response.addLink(new Link("self",   self,  "GET"));
        response.addLink(new Link("update", self,  "PUT"));
        response.addLink(new Link("delete", self,  "DELETE"));
        response.addLink(new Link("all",    base,  "GET"));
        return response;
    }

    /**
     * Returns the base URL for the parcels resource.
     * Uses {@link UriInfo} when available, falls back to a hardcoded default.
     *
     * @return base URL string e.g. {@code http://localhost:8080/api/parcels}
     */
    private String getBaseUrl() {
        try {
            return uriInfo.getBaseUri().toString() + "parcels";
        } catch (Exception e) {
            return "http://localhost:8080/api/parcels";
        }
    }
}