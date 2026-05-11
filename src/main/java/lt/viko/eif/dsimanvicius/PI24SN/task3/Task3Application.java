package lt.viko.eif.dsimanvicius.PI24SN.task3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Task3 JAX-RS Web Service application.
 *
 * <p>Starts the embedded Tomcat server and publishes the REST endpoint
 * at {@code http://localhost:8080/api/parcels}.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@SpringBootApplication
public class Task3Application {

	/**
	 * Application entry point.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		SpringApplication.run(Task3Application.class, args);
	}
}