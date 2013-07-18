package fr.ele.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface RefRestService<T> extends BaseRestService<T> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    T findByCode(@QueryParam("code") String code);
}
