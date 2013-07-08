package fr.ele.services.rest;

import javax.ws.rs.Path;

import fr.ele.model.ref.Match;

@Path("matches")
public interface MatchRestService extends RefRestService<Match> {
    public static final String SERVER = "MatchRestService";
}
