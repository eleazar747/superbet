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
import fr.ele.dto.SportDto;
import fr.ele.model.search.SportSearch;

@Path(SportRestService.PATH)
@RestService
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SportRestService {
    public static final String PATH = "sports";

    public static final String SERVER = "SportRestService";

    @GET
    Iterable<SportDto> findAll();

    @POST
    @Path("search")
    Iterable<SportDto> search(SportSearch search);

    @GET
    @Path("{id}")
    SportDto get(@PathParam("id") long id);

    @GET
    @Path("search")
    SportDto findByCode(@QueryParam("code") String code);

    @POST
    SportDto create(SportDto sport);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<SportDto> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
