package fr.ele.services.rest;

import javax.ws.rs.Path;

import fr.ele.model.ref.Sport;

@Path("sports")
public interface SportRestService extends RefRestService<Sport> {
    public static final String SERVER = "SportRestService";
}
