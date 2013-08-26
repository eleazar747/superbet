package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.ref.BookMaker;
import fr.ele.ui.rest.MetaTemplate;

@Path("bookmakers")
public interface BookMakerRestService {
    public static final String SERVER = "BookMakerRestService";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = BookMaker.class)
    List<BookMaker> findAll();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "detailview", entityClass = BookMaker.class)
    BookMaker get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = BookMaker.class)
    BookMaker findByCode(@QueryParam("code") String code);

    @POST
    @MetaTemplate(template = "detailview", entityClass = BookMaker.class)
    BookMaker insert(@FormParam("code") String code);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);
}
