package fr.ele.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.config.jaxrs.RestService;
import fr.ele.model.BookMakerSynchronization;

@Path(AdminRestService.PATH)
@RestService
public interface AdminRestService {

    public static final String PATH = "admin";

    public static final String SERVER = "AdminRestService";

    @GET
    @Path("synchronize")
    @Produces(MediaType.APPLICATION_JSON)
    BookMakerSynchronization synchronize(
            @QueryParam("bookmaker") String bookmaker);

    @GET
    @Path("syncs")
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<BookMakerSynchronization> listSyncs();
}
