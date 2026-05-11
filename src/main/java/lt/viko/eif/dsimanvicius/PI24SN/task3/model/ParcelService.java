package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.PackageItem;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * In-memory data-access service for {@link Parcel} entities.
 *
 * <p>Acts as the single source of truth for all CRUD operations performed
 * by the REST layer. In a production system this class would delegate to a
 * repository backed by a database.</p>
 *
 * <p>Follows the <em>Single Responsibility Principle</em>: this class is only
 * responsible for managing the parcel collection.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
public class ParcelService {

    /** Shared in-memory store. */
    private final List<Parcel> store = new ArrayList<>();

    /**
     * Constructs a {@code ParcelService} pre-populated with sample data.
     */
    public ParcelService() {
        seedData();
    }

    /**
     * Returns all parcels in the store.
     *
     * @return unmodifiable view of all parcels
     */
    public List<Parcel> findAll() {
        return List.copyOf(store);
    }

    /**
     * Finds a parcel by its unique identifier.
     *
     * @param id parcel id to look up
     * @return {@link Optional} containing the parcel, or empty if not found
     */
    public Optional<Parcel> findById(int id) {
        return store.stream().filter(p -> p.getId() == id).findFirst();
    }

    /**
     * Adds a new parcel to the store.
     * The id of the supplied parcel is overwritten with an auto-generated value.
     *
     * @param parcel parcel to add; must not be {@code null}
     * @return the saved parcel with its assigned id
     */
    public Parcel save(Parcel parcel) {
        int nextId = store.stream().mapToInt(Parcel::getId).max().orElse(0) + 1;
        parcel.setId(nextId);
        store.add(parcel);
        return parcel;
    }

    /**
     * Replaces an existing parcel identified by {@code id} with the supplied data.
     *
     * @param id     id of the parcel to replace
     * @param parcel replacement data
     * @return {@link Optional} with the updated parcel, or empty if id not found
     */
    public Optional<Parcel> update(int id, Parcel parcel) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId() == id) {
                parcel.setId(id);
                store.set(i, parcel);
                return Optional.of(parcel);
            }
        }
        return Optional.empty();
    }

    /**
     * Removes a parcel from the store.
     *
     * @param id id of the parcel to remove
     * @return {@code true} if a parcel was removed, {@code false} if not found
     */
    public boolean delete(int id) {
        return store.removeIf(p -> p.getId() == id);
    }

    // ------------------------------------------------------------------ //
    //  Private helpers
    // ------------------------------------------------------------------ //

    /**
     * Seeds the store with two sample parcels for demonstration purposes.
     */
    private void seedData() {
        List<PackageItem> items1 = Arrays.asList(
                new PackageItem(1, "Laptop", 2.1f, true, 'E'),
                new PackageItem(2, "Mouse",  0.2f, false, 'E')
        );
        store.add(new Parcel(1, "TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 2.3f, false, 'E', items1));

        List<PackageItem> items2 = Arrays.asList(
                new PackageItem(3, "T-Shirt", 0.3f, false, 'C'),
                new PackageItem(4, "Jeans",   0.7f, false, 'C'),
                new PackageItem(5, "Shoes",   1.0f, false, 'C')
        );
        store.add(new Parcel(2, "TRK-002-LT", "Petras Petraitis",
                "Laisves al. 10, Kaunas", 2.0f, true, 'S', items2));
    }
}