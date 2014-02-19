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

import com.codahale.metrics.annotation.Timed;

import fr.ele.config.jaxrs.RestService;
import fr.ele.model.ref.Sport;
import fr.ele.model.search.SportSearch;

@Path(SportRestService.PATH)
@RestService
public interface SportRestService {
    public static final String PATH = "sports";

    public static final String SERVER = "SportRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    Iterable<Sport> findAll();

    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Iterable<Sport> search(SportSearch search);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Sport get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    Sport findByCode(@QueryParam("code") String code);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Sport create(Sport sport);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<Sport> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
