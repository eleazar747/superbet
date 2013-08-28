package fr.ele.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path(AdminRestService.PATH)
public interface AdminRestService {

    public static final String PATH = "admin";

    public static final String SERVER = "AdminRestService";

    public static class SynchronizationResult {
        public Long nb;

        public String bookmaker;
    }

    @GET
    @Path("synchronize")
    @Produces(MediaType.APPLICATION_JSON)
    SynchronizationResult synchronize(@QueryParam("bookmaker") String bookmaker);
}
