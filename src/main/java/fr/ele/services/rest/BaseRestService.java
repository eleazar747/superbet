package fr.ele.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface BaseRestService<T> {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<T> findAll();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    T get(@PathParam("id") long id);
}
