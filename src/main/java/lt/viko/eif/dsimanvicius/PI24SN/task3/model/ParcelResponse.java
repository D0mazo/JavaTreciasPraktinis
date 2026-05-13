package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * HATEOAS-enriched response wrapper for a single {@link Parcel}.
 *
 * <p>Wraps the parcel data with a list of {@link Link} objects that
 * describe available actions the client can perform next, following
 * the HATEOAS (Hypermedia As The Engine Of Application State) principle.</p>
 *
 * <p>Example response:</p>
 * <pre>
 * {
 *   "parcel": { "id": 1, "trackingNumber": "TRK-001-LT", ... },
 *   "links": [
 *     { "rel": "self",   "href": ".../parcels/1", "method": "GET"    },
 *     { "rel": "update", "href": ".../parcels/1", "method": "PUT"    },
 *     { "rel": "delete", "href": ".../parcels/1", "method": "DELETE" },
 *     { "rel": "all",    "href": ".../parcels",   "method": "GET"    }
 *   ]
 * }
 * </pre>
 *
 * @author dsimanvicius
 * @version 1.0
 */
public class ParcelResponse {

    /** The parcel data. */
    private Parcel parcel;

    /** Hypermedia links describing available next actions. */
    private List<Link> links = new ArrayList<>();

    /**
     * Default no-argument constructor required for JSON serialisation.
     */
    public ParcelResponse() {
    }

    /**
     * Constructs a response wrapping the given parcel.
     *
     * @param parcel the parcel to wrap
     */
    public ParcelResponse(Parcel parcel) {
        this.parcel = parcel;
    }

    /** @return the wrapped parcel */
    public Parcel getParcel() { return parcel; }

    /** @param parcel the parcel to wrap */
    public void setParcel(Parcel parcel) { this.parcel = parcel; }

    /** @return list of HATEOAS links */
    public List<Link> getLinks() { return links; }

    /** @param links list of HATEOAS links */
    public void setLinks(List<Link> links) { this.links = links; }

    /**
     * Adds a single link to the response.
     *
     * @param link the link to add
     */
    public void addLink(Link link) { this.links.add(link); }
}