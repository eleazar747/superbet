package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.ref.BookMaker;

@Path("bookmaker")
public interface BookMakerRestService {
    public static final String SERVER = "BookMakerRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookMaker> findAll();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    BookMaker get(@PathParam("id") long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    BookMaker findByCode(@QueryParam("code") String code);
}
