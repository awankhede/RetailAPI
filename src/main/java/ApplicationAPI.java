import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

@ApplicationPath("/")
public class ApplicationAPI extends Application {
    private final static Logger LOGGER = Logger.getLogger(ApplicationAPI.class.getName());

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myRetail/";

    public static HttpServer startServer() {

        /* Resource config which scans for JAX-RS resources & providers */
        final ResourceConfig rc = new ResourceConfig().packages("com.myretail.rest");

        /* Creates and sets up a new instances of the (grizzly) server. The app is hosted at the URI specified */
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        LOGGER.info("myRetail App has started and hosted on: " + BASE_URI);

    }
}
