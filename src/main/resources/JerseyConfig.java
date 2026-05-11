package lt.viko.eif.dsimanvicius.PI24SN.task3.config;

import lt.viko.eif.dsimanvicius.PI24SN.task3.resource.ParcelResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey (JAX-RS) application configuration.
 *
 * <p>Sets the root path of the REST API to {@code /api} and registers
 * all resource classes. Annotated with {@link Component} so Spring Boot
 * discovers and manages it automatically.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    /**
     * Constructs the Jersey configuration and registers resource classes.
     */
    public JerseyConfig() {
        register(ParcelResource.class);
    }
}