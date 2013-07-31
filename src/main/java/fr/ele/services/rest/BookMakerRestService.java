package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.ref.BookMaker;
import fr.ele.ui.MetaTemplate;

@Path("bookmakers")
public interface BookMakerRestService {
    public static final String SERVER = "BookMakerRestService";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "table", entityClass = BookMaker.class)
    List<BookMaker> findAll();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "detail", entityClass = BookMaker.class)
    BookMaker get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "table", entityClass = BookMaker.class)
    BookMaker findByCode(@QueryParam("code") String code);

}
