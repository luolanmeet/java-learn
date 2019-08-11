package pers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/user")
public interface IUserService {

    @GET
    @Path("/say/{info}")
    String sayHello(@PathParam("info") String info);

}
