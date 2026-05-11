package lt.viko.eif.dsimanvicius.PI24SN.task3.model;

/**
 * Represents a single item contained within a parcel.
 *
 * <p>Each {@code PackageItem} describes one physical product unit
 * that is packed inside a {@link Parcel}.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
public class PackageItem {

    /** Unique identifier of the item. */
    private int id;

    /** Name/description of the item. */
    private String name;

    /** Weight of the item in kilograms. */
    private float weightKg;

    /** Whether the item is classified as fragile. */
    private boolean fragile;

    /** Single-letter category code (e.g. 'E' = Electronics, 'C' = Clothing). */
    private char categoryCode;

    /**
     * Default no-argument constructor required for JAX-RS / JSON serialisation.
     */
    public PackageItem() {
    }

    /**
     * Constructs a fully initialised {@code PackageItem}.
     *
     * @param id           unique item identifier
     * @param name         human-readable item name
     * @param weightKg     weight in kilograms
     * @param fragile      {@code true} if the item requires fragile handling
     * @param categoryCode single-character category code
     */
    public PackageItem(int id, String name, float weightKg,
                       boolean fragile, char categoryCode) {
        this.id           = id;
        this.name         = name;
        this.weightKg     = weightKg;
        this.fragile      = fragile;
        this.categoryCode = categoryCode;
    }

    // ------------------------------------------------------------------ //
    //  Getters & Setters
    // ------------------------------------------------------------------ //

    /**
     * Returns the item identifier.
     *
     * @return item id
     */
    public int getId() { return id; }

    /**
     * Sets the item identifier.
     *
     * @param id item id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the item name.
     *
     * @return item name
     */
    public String getName() { return name; }

    /**
     * Sets the item name.
     *
     * @param name item name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the weight in kilograms.
     *
     * @return weight (kg)
     */
    public float getWeightKg() { return weightKg; }

    /**
     * Sets the weight in kilograms.
     *
     * @param weightKg weight (kg)
     */
    public void setWeightKg(float weightKg) { this.weightKg = weightKg; }

    /**
     * Returns whether the item is fragile.
     *
     * @return {@code true} if fragile
     */
    public boolean isFragile() { return fragile; }

    /**
     * Sets the fragile flag.
     *
     * @param fragile {@code true} if the item requires fragile handling
     */
    public void setFragile(boolean fragile) { this.fragile = fragile; }

    /**
     * Returns the single-character category code.
     *
     * @return category code
     */
    public char getCategoryCode() { return categoryCode; }

    /**
     * Sets the single-character category code.
     *
     * @param categoryCode category code
     */
    public void setCategoryCode(char categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * Returns a human-readable representation of this item.
     *
     * @return string representation
     */
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