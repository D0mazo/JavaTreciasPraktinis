package lt.viko.eif.dsimanvicius.PI24SN.task3.config;

import lt.viko.eif.dsimanvicius.PI24SN.task3.resource.OpenApiResource;
import lt.viko.eif.dsimanvicius.PI24SN.task3.resource.ParcelResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Jersey (JAX-RS) application configuration.
 *
 * <p>Registers all JAX-RS resource classes. The base path {@code /api}
 * is set via {@code spring.jersey.application-path} in
 * {@code application.properties}.</p>
 *
 * @author dsimanvicius
 * @version 1.0
 */
@Component
public class JerseyConfig extends ResourceConfig {

    /**
     * Constructs the Jersey configuration and registers resource classes.
     */
    public JerseyConfig() {
        register(ParcelResource.class);
        register(OpenApiResource.class);
    }
}