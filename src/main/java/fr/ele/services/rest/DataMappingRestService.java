package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import fr.ele.config.jaxrs.RestService;
import fr.ele.dto.DataMappingDto;
import fr.ele.model.search.DataMappingSearch;

@Path(DataMappingRestService.PATH)
@RestService
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DataMappingRestService {

    public static final String PATH = "datamappings";

    static final String SERVER = "DataMappingRestService";

    @GET
    Iterable<DataMappingDto> findAll();

    @POST
    @Path("search")
    Iterable<DataMappingDto> search(DataMappingSearch search);

    @GET
    @Path("{id}")
    DataMappingDto get(@PathParam("id") long id);

    @POST
    DataMappingDto create(DataMappingDto dataMapping);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    List<DataMappingDto> insertCsv(
            @Multipart(value = "content", type = MediaType.WILDCARD) Attachment file);
}
