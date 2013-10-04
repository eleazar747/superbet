package fr.ele.services.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(BetRestService.PATH)
public interface BetRestService {
	public static final String PATH = "bets";

	public static final String SERVER = "BetRestService";

	public static final class SureBetDto {
		public String sport, betType, match;

		public Map<String, Double> alternatives;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sures")
	List<SureBetDto> getSureBets();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Iterable<BetDto> getBets();

	public static class BetDto {
		public Double value;
		public String type, bookmaker, match, alternative, sport;
		public Date matchDate;
	}
}
