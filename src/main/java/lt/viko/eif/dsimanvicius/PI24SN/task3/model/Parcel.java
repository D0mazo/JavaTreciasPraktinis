package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a logistics parcel stored in the SQLite database.
 *
 * <p>Mapped to the {@code parcel} table. Each parcel owns a collection of
 * {@link PackageItem} objects stored in the {@code package_item} table,
 * linked by a one-to-many relationship with cascade persistence.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Entity
@Table(name = "parcel")
public class Parcel {

    /** Primary key — auto-incremented by the database. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Tracking number printed on the shipping label. */
    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    /** Full name of the recipient. */
    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    /** Formatted delivery address. */
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    /** Total declared weight in kilograms. */
    @Column(name = "total_weight_kg", nullable = false)
    private float totalWeightKg;

    /** Whether the parcel has already been delivered. */
    @Column(nullable = false)
    private boolean delivered;

    /**
     * Priority class: 'S' = Standard, 'E' = Express, 'O' = Overnight.
     */
    @Column(name = "priority_class", nullable = false)
    private char priorityClass;

    /**
     * Items contained in this parcel.
     * Cascade ALL ensures items are saved/deleted with the parcel.
     */
    @OneToMany(mappedBy = "parcel",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<PackageItem> items = new ArrayList<>();

    /**
     * Default no-argument constructor required by JPA and JAX-RS serialisation.
     */
    public Parcel() {
    }

    /**
     * Constructs a fully initialised {@code Parcel}.
     *
     * @param trackingNumber  shipping label tracking code
     * @param recipientName   full name of the recipient
     * @param deliveryAddress formatted delivery address
     * @param totalWeightKg   declared total weight in kg
     * @param delivered       {@code true} if already delivered
     * @param priorityClass   single-character priority code
     */
    public Parcel(String trackingNumber, String recipientName,
                  String deliveryAddress, float totalWeightKg,
                  boolean delivered, char priorityClass) {
        this.trackingNumber  = trackingNumber;
        this.recipientName   = recipientName;
        this.deliveryAddress = deliveryAddress;
        this.totalWeightKg   = totalWeightKg;
        this.delivered       = delivered;
        this.priorityClass   = priorityClass;
    }

    /** @return parcel primary key */
    public int getId() { return id; }

    /** @param id parcel primary key */
    public void setId(int id) { this.id = id; }

    /** @return tracking number */
    public String getTrackingNumber() { return trackingNumber; }

    /** @param trackingNumber tracking number */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /** @return recipient full name */
    public String getRecipientName() { return recipientName; }

    /** @param recipientName recipient full name */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /** @return delivery address */
    public String getDeliveryAddress() { return deliveryAddress; }

    /** @param deliveryAddress delivery address */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /** @return total weight in kg */
    public float getTotalWeightKg() { return totalWeightKg; }

    /** @param totalWeightKg total weight in kg */
    public void setTotalWeightKg(float totalWeightKg) {
        this.totalWeightKg = totalWeightKg;
    }

    /** @return {@code true} if delivered */
    public boolean isDelivered() { return delivered; }

    /** @param delivered delivery status */
    public void setDelivered(boolean delivered) { this.delivered = delivered; }

    /** @return priority class character */
    public char getPriorityClass() { return priorityClass; }

    /** @param priorityClass priority class character */
    public void setPriorityClass(char priorityClass) {
        this.priorityClass = priorityClass;
    }

    /** @return mutable list of items */
    public List<PackageItem> getItems() { return items; }

    /**
     * Replaces the item list and re-links each item's parcel reference.
     *
     * @param items new list of items
     */
    public void setItems(List<PackageItem> items) {
        this.items.clear();
        if (items != null) {
            items.forEach(item -> {
                item.setParcel(this);
                this.items.add(item);
            });
        }
    }

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