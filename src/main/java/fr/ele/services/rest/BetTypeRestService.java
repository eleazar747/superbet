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

import fr.ele.model.ref.BetType;
import fr.ele.model.search.BetTypeSearch;

@Path(BetTypeRestService.PATH)
public interface BetTypeRestService {
    public static final String PATH = "bettypes";

    public static final String SERVER = "BetTypeRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<BetType> findAll();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    BetType get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    BetType findByCode(@QueryParam("code") String code);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BetType create(BetType betType);

    @POST
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<BetType> search(BetTypeSearch betTypeSearch);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<BetType> insertCsv(
            @Multipart(value = "content", type = "text/csv") Attachment file);
}
