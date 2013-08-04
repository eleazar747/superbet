package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.ele.model.DataMapping;
import fr.ele.ui.MetaTemplate;

@Path("datamappings")
public interface DataMappingRestService {

    static final String SERVER = "DataMappingRestService";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "refview", entityClass = DataMapping.class)
    List<DataMapping> findAll();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @MetaTemplate(template = "detail", entityClass = DataMapping.class)
    DataMapping get(@PathParam("id") long id);
}
