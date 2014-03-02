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

import fr.ele.config.jaxrs.RestService;
import fr.ele.dto.BetTypeDto;
import fr.ele.model.search.BetTypeSearch;

@Path(BetTypeRestService.PATH)
@RestService
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BetTypeRestService {
    public static final String PATH = "bettypes";

    public static final String SERVER = "BetTypeRestService";

    @GET
    Iterable<BetTypeDto> findAll();

    @GET
    @Path("{id}")
    BetTypeDto get(@PathParam("id") long id);

    @GET
    @Path("search")
    BetTypeDto findByCode(@QueryParam("code") String code);

    @POST
    BetTypeDto create(BetTypeDto betType);

    @POST
    @Path("search")
    Iterable<BetTypeDto> search(BetTypeSearch betTypeSearch);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<BetTypeDto> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
