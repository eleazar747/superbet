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
import fr.ele.dto.MatchDto;
import fr.ele.model.search.MatchSearch;

@Path(MatchRestService.PATH)
@RestService
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MatchRestService {
    public static final String PATH = "matches";

    public static final String SERVER = "MatchRestService";

    @GET
    public Iterable<MatchDto> findAll();

    @POST
    @Path("search")
    public Iterable<MatchDto> search(MatchSearch search);

    @GET
    @Path("{id}")
    MatchDto get(@PathParam("id") long id);

    @GET
    @Path("search")
    MatchDto findByCode(@QueryParam("code") String code);

    @POST
    MatchDto create(MatchDto match);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<MatchDto> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
