package lt.viko.eif.dsimanvicius.PI24SN.task3.resource;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.service.Parcelservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * JAX-RS REST resource that exposes CRUD operations for {@link Parcel} entities.
 *
 * <p>Base path: {@code /api/parcels}</p>
 *
 * <table border="1">
 *   <caption>Available endpoints</caption>
 *   <tr><th>Method</th><th>Path</th><th>Description</th></tr>
 *   <tr><td>GET</td>   <td>/api/parcels</td>        <td>Retrieve all parcels</td></tr>
 *   <tr><td>GET</td>   <td>/api/parcels/{id}</td>   <td>Retrieve parcel by id</td></tr>
 *   <tr><td>POST</td>  <td>/api/parcels</td>        <td>Create a new parcel</td></tr>
 *   <tr><td>PUT</td>   <td>/api/parcels/{id}</td>   <td>Update existing parcel</td></tr>
 *   <tr><td>DELETE</td><td>/api/parcels/{id}</td>   <td>Delete a parcel</td></tr>
 * </table>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Path("/parcels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParcelResource {

    /** Service layer handling all persistence logic. */
    private final Parcelservice parcelService = new Parcelservice();

    /**
     * Retrieves every parcel stored in the system.
     *
     * <p>HTTP {@code GET /api/parcels}</p>
     *
     * @return {@code 200 OK} with a JSON array of all parcels
     */
    @GET
    public Response getAllParcels() {
        List<Parcel> parcels = parcelService.findAll();
        return Response.ok(parcels).build();
    }

    /**
     * Retrieves a single parcel by its unique identifier.
     *
     * <p>HTTP {@code GET /api/parcels/{id}}</p>
     *
     * @param id path parameter – parcel identifier
     * @return {@code 200 OK} with the parcel JSON, or {@code 404 Not Found}
     */
    @GET
    @Path("/{id}")
    public Response getParcelById(@PathParam("id") int id) {
        Optional<Parcel> result = parcelService.findById(id);
        if (result.isPresent()) {
            return Response.ok(result.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Parcel with id " + id + " not found\"}")
                .build();
    }

    /**
     * Creates a new parcel.
     *
     * <p>HTTP {@code POST /api/parcels}</p>
     * <p>Request body must be a valid JSON representation of {@link Parcel}.</p>
     *
     * @param parcel deserialized parcel from the request body
     * @return {@code 201 Created} with the saved parcel including its assigned id
     */
    @POST
    public Response createParcel(Parcel parcel) {
        Parcel saved = parcelService.save(parcel);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    /**
     * Fully replaces an existing parcel.
     *
     * <p>HTTP {@code PUT /api/parcels/{id}}</p>
     *
     * @param id     path parameter – id of the parcel to replace
     * @param parcel replacement parcel data from the request body
     * @return {@code 200 OK} with the updated parcel, or {@code 404 Not Found}
     */
    @PUT
    @Path("/{id}")
    public Response updateParcel(@PathParam("id") int id, Parcel parcel) {
        Optional<Parcel> updated = parcelService.update(id, parcel);
        if (updated.isPresent()) {
            return Response.ok(updated.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Parcel with id " + id + " not found\"}")
                .build();
    }

    /**
     * Deletes a parcel identified by its id.
     *
     * <p>HTTP {@code DELETE /api/parcels/{id}}</p>
     *
     * @param id path parameter – id of the parcel to delete
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
}