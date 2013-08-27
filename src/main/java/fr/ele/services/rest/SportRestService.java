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

import fr.ele.model.ref.Sport;

@Path(SportRestService.PATH)
public interface SportRestService {
    public static final String PATH = "sports";

    public static final String SERVER = "SportRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sport> findAll();

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
            @Multipart(value = "content", type = "text/csv") Attachment file);
}
