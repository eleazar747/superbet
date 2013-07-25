package fr.ele.services.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.ele.model.Bet;
import fr.ele.ui.Template;

@Path("bets")
public interface BetRestService {
    public static final String SERVER = "BetRestService";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Template("bets")
    List<Bet> getBets();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Bet> getBets(@QueryParam("date") Date date);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("best")
    List<Bet> getBestBets(@QueryParam("date") Date date);

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // @Path("sures")
    // List<SureBet> getSureBets(@QueryParam("date") Date date);
}
