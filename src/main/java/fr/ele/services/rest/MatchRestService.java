package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import fr.ele.model.ref.Match;

@Path(MatchRestService.PATH)
public interface MatchRestService {
    public static final String PATH = "matches";

    public static final String SERVER = "MatchRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Match> findAll();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Match get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    Match findByCode(@QueryParam("code") String code);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Match create(Match match);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<Match> insertCsv(
            @Multipart(value = "content", type = "text/csv") Attachment file);
}
