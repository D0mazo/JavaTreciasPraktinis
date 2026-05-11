package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a logistics parcel that groups one or more {@link PackageItem} objects.
 *
 * <p>A {@code Parcel} is the primary domain entity exposed by the REST API.
 * It holds delivery metadata and an ordered list of items packed inside it.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
public class Parcel {

    /** Unique identifier of the parcel. */
    private int id;

    /** Tracking number visible on the shipping label. */
    private String trackingNumber;

    /** Full name of the recipient. */
    private String recipientName;

    /** Delivery address as a single formatted string. */
    private String deliveryAddress;

    /** Total declared weight of the parcel in kilograms. */
    private float totalWeightKg;

    /** Whether the parcel has already been delivered. */
    private boolean delivered;

    /**
     * Priority class: 'S' = Standard, 'E' = Express, 'O' = Overnight.
     */
    private char priorityClass;

    /** Ordered list of items contained in this parcel. */
    private List<PackageItem> items;

    /**
     * Default no-argument constructor required for JAX-RS / JSON serialisation.
     * Initialises {@code items} to an empty list.
     */
    public Parcel() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructs a fully initialised {@code Parcel}.
     *
     * @param id              unique parcel identifier
     * @param trackingNumber  human-readable tracking code
     * @param recipientName   full name of the recipient
     * @param deliveryAddress formatted delivery address
     * @param totalWeightKg   declared total weight in kg
     * @param delivered       {@code true} if already delivered
     * @param priorityClass   single-character priority code
     * @param items           list of {@link PackageItem} objects; must not be {@code null}
     */
    public Parcel(int id, String trackingNumber, String recipientName,
                  String deliveryAddress, float totalWeightKg,
                  boolean delivered, char priorityClass,
                  List<PackageItem> items) {
        this.id              = id;
        this.trackingNumber  = trackingNumber;
        this.recipientName   = recipientName;
        this.deliveryAddress = deliveryAddress;
        this.totalWeightKg   = totalWeightKg;
        this.delivered       = delivered;
        this.priorityClass   = priorityClass;
        this.items           = items != null ? items : new ArrayList<>();
    }

    // ------------------------------------------------------------------ //
    //  Getters & Setters
    // ------------------------------------------------------------------ //

    /**
     * Returns the parcel identifier.
     *
     * @return parcel id
     */
    public int getId() { return id; }

    /**
     * Sets the parcel identifier.
     *
     * @param id parcel id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the tracking number.
     *
     * @return tracking number string
     */
    public String getTrackingNumber() { return trackingNumber; }

    /**
     * Sets the tracking number.
     *
     * @param trackingNumber tracking number string
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * Returns the recipient's full name.
     *
     * @return recipient name
     */
    public String getRecipientName() { return recipientName; }

    /**
     * Sets the recipient's full name.
     *
     * @param recipientName recipient name
     */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /**
     * Returns the delivery address.
     *
     * @return delivery address
     */
    public String getDeliveryAddress() { return deliveryAddress; }

    /**
     * Sets the delivery address.
     *
     * @param deliveryAddress formatted delivery address
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Returns the total declared weight in kilograms.
     *
     * @return weight (kg)
     */
    public float getTotalWeightKg() { return totalWeightKg; }

    /**
     * Sets the total declared weight.
     *
     * @param totalWeightKg weight (kg)
     */
    public void setTotalWeightKg(float totalWeightKg) {
        this.totalWeightKg = totalWeightKg;
    }

    /**
     * Returns whether the parcel has been delivered.
     *
     * @return {@code true} if delivered
     */
    public boolean isDelivered() { return delivered; }

    /**
     * Sets the delivery status.
     *
     * @param delivered {@code true} if delivered
     */
    public void setDelivered(boolean delivered) { this.delivered = delivered; }

    /**
     * Returns the priority class code.
     *
     * @return priority class character
     */
    public char getPriorityClass() { return priorityClass; }

    /**
     * Sets the priority class code.
     *
     * @param priorityClass priority class character
     */
    public void setPriorityClass(char priorityClass) {
        this.priorityClass = priorityClass;
    }

    /**
     * Returns the list of items in this parcel.
     *
     * @return mutable list of {@link PackageItem} objects
     */
    public List<PackageItem> getItems() { return items; }

    /**
     * Replaces the item list entirely.
     *
     * @param items new list of {@link PackageItem} objects
     */
    public void setItems(List<PackageItem> items) { this.items = items; }

    /**
     * Returns a human-readable representation of this parcel.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Parcel{"
                + "id=" + id
                + ", trackingNumber='" + trackingNumber + '\''
                + ", recipientName='" + recipientName + '\''
                + ", deliveryAddress='" + deliveryAddress + '\''
                + ", totalWeightKg=" + totalWeightKg
                + ", delivered=" + delivered
                + ", priorityClass=" + priorityClass
                + ", items=" + items
                + '}';
    }
}