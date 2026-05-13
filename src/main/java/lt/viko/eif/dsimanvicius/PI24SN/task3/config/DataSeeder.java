package lt.viko.eif.dsimanvicius.PI24SN.task3.config;

import lt.viko.eif.dsimanvicius.PI24SN.task3.model.PackageItem;
import lt.viko.eif.dsimanvicius.PI24SN.task3.model.Parcel;
import lt.viko.eif.dsimanvicius.PI24SN.task3.repository.ParcelRepository;
import lt.viko.eif.dsimanvicius.PI24SN.task3.service.TestFileGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Seeds the SQLite database with sample data and triggers generation
 * of all test/documentation files on application startup.
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
public class DataSeeder implements CommandLineRunner {

    /** Repository used to check and insert seed data. */
    private final ParcelRepository parcelRepository;

    /** Service that generates SoapUI, Postman, OpenAPI and Swagger files. */
    private final TestFileGeneratorService testFileGeneratorService;

    /**
     * Constructs the seeder with injected dependencies.
     *
     * @param parcelRepository         JPA repository for parcel entities
     * @param testFileGeneratorService service for generating test files
     */
    public DataSeeder(ParcelRepository parcelRepository,
                      TestFileGeneratorService testFileGeneratorService) {
        this.parcelRepository         = parcelRepository;
        this.testFileGeneratorService = testFileGeneratorService;
    }

    /**
     * Seeds the database if empty, then generates all test files.
     *
     * @param args command-line arguments (not used)
     */
    @Override
    public void run(String... args) {

        if (parcelRepository.count() == 0) {
            Parcel parcel1 = new Parcel(
                    "TRK-001-LT", "Jonas Jonaitis",
                    "Gedimino pr. 1, Vilnius", 2.3f, false, 'E');

            PackageItem laptop = new PackageItem("Laptop", 2.1f, true, 'E');
            PackageItem mouse  = new PackageItem("Mouse",  0.2f, false, 'E');
            laptop.setParcel(parcel1);
            mouse.setParcel(parcel1);
            parcel1.getItems().addAll(Arrays.asList(laptop, mouse));

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

        testFileGeneratorService.generateAll();
    }
}