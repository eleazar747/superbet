package fr.ele.services.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.ele.config.jaxrs.RestService;
import fr.ele.dto.BetDto;
import fr.ele.model.search.BetSearch;

@Path(BetRestService.PATH)
@RestService
public interface BetRestService {
    public static final String PATH = "bets";

    public static final String SERVER = "BetRestService";

    public static final class SureBetDto {
        public String sport, betType, match;

        public String odds, date, profit;

        public Map<String, Double> alternatives;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sures")
    List<SureBetDto> getSureBets();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("search")
    Iterable<BetDto> search(BetSearch search);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Iterable<BetDto> getBets();

}
