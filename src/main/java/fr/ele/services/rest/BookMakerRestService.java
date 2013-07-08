package fr.ele.services.rest;

import javax.ws.rs.Path;

import fr.ele.model.ref.BookMaker;

@Path("bookmakers")
public interface BookMakerRestService extends RefRestService<BookMaker> {
    public static final String SERVER = "BookMakerRestService";

}
