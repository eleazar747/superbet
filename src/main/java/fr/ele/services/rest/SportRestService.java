package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.ref.Sport;
import fr.ele.ui.MetaTemplate;

@Path("sports")
public interface SportRestService {
    public static final String SERVER = "SportRestService";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = Sport.class)
    public List<Sport> findAll();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "detail", entityClass = Sport.class)
    Sport get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = Sport.class)
    Sport findByCode(@QueryParam("code") String code);
}
