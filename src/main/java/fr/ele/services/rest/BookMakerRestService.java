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

import fr.ele.model.ref.BookMaker;
import fr.ele.model.search.BookmakerSearch;

@Path(BookMakerRestService.PATH)
public interface BookMakerRestService {

    public static final String PATH = "bookmakers";

    public static final String SERVER = "BookMakerRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<BookMaker> findAll();

    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Iterable<BookMaker> search(BookmakerSearch search);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    BookMaker get(@PathParam("id") long id);

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    BookMaker findByCode(@QueryParam("code") String code);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BookMaker create(BookMaker bookmaker);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<BookMaker> insertCsv(
            @Multipart(value = "content", type = "text/csv") Attachment file);
}
