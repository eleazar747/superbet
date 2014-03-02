package fr.ele.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.ele.config.jaxrs.RestService;
import fr.ele.dto.UnMatchedPalyerDto;
import fr.ele.model.search.UnMatchedPlayerSearch;

@Path(UnMatchedPlayerRestService.PATH)
@RestService
public interface UnMatchedPlayerRestService {
    public static final String PATH = "unmatchedplayer";

    public static final String SERVER = "UnMatchedPlayerRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<UnMatchedPalyerDto> findAll();

    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Iterable<UnMatchedPalyerDto> search(UnMatchedPlayerSearch search);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @GET
    @Path("{id}")
    UnMatchedPalyerDto get(@PathParam("id") long id);
}
