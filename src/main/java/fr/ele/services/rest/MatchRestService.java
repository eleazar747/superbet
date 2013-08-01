package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.ref.Match;
import fr.ele.ui.MetaTemplate;

@Path("matches")
public interface MatchRestService {
    public static final String SERVER = "MatchRestService";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = Match.class)
    public List<Match> findAll();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "detail", entityClass = Match.class)
    Match get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = Match.class)
    Match findByCode(@QueryParam("code") String code);
}
