package lt.viko.eif.dsimanvicius.PI24SN.task3.config;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.PackageItem;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.repository.ParcelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Seeds the SQLite database with sample parcels on the first application start.
 *
 * <p>Implements {@link CommandLineRunner} so it runs automatically after the
 * Spring context is fully initialised. Checks whether data already exists
 * before inserting to avoid duplicates on subsequent restarts.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
public class DataSeeder implements CommandLineRunner {

    /** Repository used to check and insert seed data. */
    private final ParcelRepository parcelRepository;

    /**
     * Constructs the seeder with the injected repository.
     *
     * @param parcelRepository JPA repository for parcel entities
     */
    public DataSeeder(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    /**
     * Inserts sample parcels into the database if the table is empty.
     *
     * @param args command-line arguments (not used)
     */
    @Override
    public void run(String... args) {
        if (parcelRepository.count() > 0) {
            return; // already seeded — skip
        }

        // ── Parcel 1 ──────────────────────────────────────────────────
        Parcel parcel1 = new Parcel(
                "TRK-001-LT", "Jonas Jonaitis",
                "Gedimino pr. 1, Vilnius", 2.3f, false, 'E');

        PackageItem laptop = new PackageItem("Laptop", 2.1f, true, 'E');
        PackageItem mouse  = new PackageItem("Mouse",  0.2f, false, 'E');
        laptop.setParcel(parcel1);
        mouse.setParcel(parcel1);
        parcel1.getItems().addAll(Arrays.asList(laptop, mouse));

        // ── Parcel 2 ──────────────────────────────────────────────────
        Parcel parcel2 = new Parcel(
                "TRK-002-LT", "Petras Petraitis",
                "Laisves al. 10, Kaunas", 2.0f, true, 'S');

        PackageItem shirt = new PackageItem("T-Shirt", 0.3f, false, 'C');
        PackageItem jeans = new PackageItem("Jeans",   0.7f, false, 'C');
        PackageItem shoes = new PackageItem("Shoes",   1.0f, false, 'C');
        shirt.setParcel(parcel2);
        jeans.setParcel(parcel2);
        shoes.setParcel(parcel2);
        parcel2.getItems().addAll(Arrays.asList(shirt, jeans, shoes));

        parcelRepository.save(parcel1);
        parcelRepository.save(parcel2);

        System.out.println("✅ Database seeded with 2 sample parcels.");
    }
}