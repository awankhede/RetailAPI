package Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/myRetail")
public class RetailController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage(){
        String returnString = "Hello, World!";
        return returnString;
    }

}
