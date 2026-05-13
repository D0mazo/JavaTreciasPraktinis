package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

/**
 * Represents a single HATEOAS hypermedia link.
 *
 * <p>Each {@code Link} describes one action the client can perform,
 * including the target URL, HTTP method, and a human-readable relation name.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
public class Link {

    /** Relation name describing the link's purpose (e.g. "self", "delete"). */
    private String rel;

    /** Target URL of the action. */
    private String href;

    /** HTTP method to use (GET, POST, PUT, DELETE). */
    private String method;

    /**
     * Default no-argument constructor required for JSON serialisation.
     */
    public Link() {
    }

    /**
     * Constructs a fully initialised {@code Link}.
     *
     * @param rel    relation name
     * @param href   target URL
     * @param method HTTP method
     */
    public Link(String rel, String href, String method) {
        this.rel    = rel;
        this.href   = href;
        this.method = method;
    }

    /** @return relation name */
    public String getRel() { return rel; }

    /** @param rel relation name */
    public void setRel(String rel) { this.rel = rel; }

    /** @return target URL */
    public String getHref() { return href; }

    /** @param href target URL */
    public void setHref(String href) { this.href = href; }

    /** @return HTTP method */
    public String getMethod() { return method; }

    /** @param method HTTP method */
    public void setMethod(String method) { this.method = method; }
}