package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * JPA entity representing a single item contained within a {@link Parcel}.
 *
 * <p>Mapped to the {@code package_item} table in the SQLite database.
 * Each item belongs to exactly one parcel via a many-to-one relationship.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Entity
@Table(name = "package_item")
public class PackageItem {

    /** Primary key — auto-incremented by the database. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Human-readable name of the item. */
    @Column(nullable = false)
    private String name;

    /** Weight of the item in kilograms. */
    @Column(name = "weight_kg", nullable = false)
    private float weightKg;

    /** Whether the item requires fragile handling. */
    @Column(nullable = false)
    private boolean fragile;

    /** Single-letter category code (e.g. 'E' = Electronics, 'C' = Clothing). */
    @Column(name = "category_code", nullable = false)
    private char categoryCode;

    /** The parcel this item belongs to. Hidden from JSON serialisation. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_id")
    @JsonIgnore
    private Parcel parcel;

    /**
     * Default no-argument constructor required by JPA and JAX-RS serialisation.
     */
    public PackageItem() {
    }

    /**
     * Constructs a fully initialised {@code PackageItem}.
     *
     * @param name         human-readable item name
     * @param weightKg     weight in kilograms
     * @param fragile      {@code true} if fragile handling is required
     * @param categoryCode single-character category code
     */
    public PackageItem(String name, float weightKg,
                       boolean fragile, char categoryCode) {
        this.name         = name;
        this.weightKg     = weightKg;
        this.fragile      = fragile;
        this.categoryCode = categoryCode;
    }

    // ------------------------------------------------------------------ //
    //  Getters & Setters
    // ------------------------------------------------------------------ //

    /** @return item primary key */
    public int getId() { return id; }

    /** @param id item primary key */
    public void setId(int id) { this.id = id; }

    /** @return item name */
    public String getName() { return name; }

    /** @param name item name */
    public void setName(String name) { this.name = name; }

    /** @return weight in kg */
    public float getWeightKg() { return weightKg; }

    /** @param weightKg weight in kg */
    public void setWeightKg(float weightKg) { this.weightKg = weightKg; }

    /** @return {@code true} if fragile */
    public boolean isFragile() { return fragile; }

    /** @param fragile fragile flag */
    public void setFragile(boolean fragile) { this.fragile = fragile; }

    /** @return category code character */
    public char getCategoryCode() { return categoryCode; }

    /** @param categoryCode category code character */
    public void setCategoryCode(char categoryCode) {
        this.categoryCode = categoryCode;
    }

    /** @return the parent parcel */
    public Parcel getParcel() { return parcel; }

    /** @param parcel the parent parcel */
    public void setParcel(Parcel parcel) { this.parcel = parcel; }

    @Override
    public String toString() {
        return "PackageItem{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", weightKg=" + weightKg
                + ", fragile=" + fragile
                + ", categoryCode=" + categoryCode
                + '}';
    }
}