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
import fr.ele.dto.BookmakerDto;
import fr.ele.model.search.BookmakerSearch;

@Path(BookMakerRestService.PATH)
@RestService
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookMakerRestService {

    public static final String PATH = "bookmakers";

    public static final String SERVER = "BookMakerRestService";

    @GET
    Iterable<BookmakerDto> findAll();

    @POST
    @Path("search")
    Iterable<BookmakerDto> search(BookmakerSearch search);

    @GET
    @Path("{id}")
    BookmakerDto get(@PathParam("id") long id);

    @GET
    @Path("search")
    BookmakerDto findByCode(@QueryParam("code") String code);

    @POST
    BookmakerDto create(BookmakerDto bookmaker);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<BookmakerDto> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
