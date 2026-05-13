package lt.viko.eif.dsimanvicius.PI24SN.task3.repository;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Parcel} entities.
 *
 * <p>Provides all standard CRUD operations against the SQLite
 * {@code parcel} table with no boilerplate implementation required.</p>
 *
 * <p>Spring generates the implementation at runtime — no class body needed.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
}