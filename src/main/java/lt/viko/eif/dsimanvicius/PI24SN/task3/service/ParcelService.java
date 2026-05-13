package lt.viko.eif.dsimanvicius.PI24SN.task3.service;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for {@link Parcel} CRUD operations backed by SQLite via JPA.
 *
 * <p>Delegates all persistence to {@link ParcelRepository}. This class is
 * responsible only for business logic — it does not handle HTTP concerns.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Service
public class ParcelService {

    /** JPA repository providing database access. */
    private final ParcelRepository parcelRepository;

    /**
     * Constructs the service with the injected repository.
     *
     * @param parcelRepository Spring Data JPA repository for parcels
     */
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    /**
     * Returns all parcels stored in the database.
     *
     * @return list of all {@link Parcel} entities
     */
    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    /**
     * Finds a parcel by its primary key.
     *
     * @param id parcel identifier
     * @return {@link Optional} containing the parcel, or empty if not found
     */
    public Optional<Parcel> findById(int id) {
        return parcelRepository.findById(id);
    }

    /**
     * Persists a new parcel (and its items) to the database.
     *
     * @param parcel parcel to save; id will be assigned by the database
     * @return the saved parcel with its generated id
     */
    public Parcel save(Parcel parcel) {
        // Re-link each item to the parcel before saving
        if (parcel.getItems() != null) {
            parcel.getItems().forEach(item -> item.setParcel(parcel));
        }
        return parcelRepository.save(parcel);
    }

    /**
     * Replaces an existing parcel identified by {@code id}.
     *
     * @param id     id of the parcel to replace
     * @param parcel replacement data
     * @return {@link Optional} with the updated parcel, or empty if not found
     */
    public Optional<Parcel> update(int id, Parcel parcel) {
        if (!parcelRepository.existsById(id)) {
            return Optional.empty();
        }
        parcel.setId(id);
        if (parcel.getItems() != null) {
            parcel.getItems().forEach(item -> item.setParcel(parcel));
        }
        return Optional.of(parcelRepository.save(parcel));
    }

    /**
     * Deletes a parcel and all its items from the database.
     *
     * @param id id of the parcel to delete
     * @return {@code true} if deleted, {@code false} if id not found
     */
    public boolean delete(int id) {
        if (!parcelRepository.existsById(id)) {
            return false;
        }
        parcelRepository.deleteById(id);
        return true;
    }
}