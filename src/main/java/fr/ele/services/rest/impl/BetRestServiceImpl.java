package fr.ele.services.rest.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.Bet;
import fr.ele.model.QBet;
import fr.ele.model.ref.RefKey;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.bets.BetService;
import fr.ele.services.bets.SureBet;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.BetRestService;

@Service(BetRestService.SERVER)
@Transactional(readOnly = true)
public class BetRestServiceImpl implements BetRestService {

	@Autowired
	private BetService betService;

	@Autowired
	private BetRepository betRepository;

	@Override
	public List<SureBetDto> getSureBets() {
		BetSearch search = new BetSearch();
		search.setFrom(DateMidnight.now().toDate());
		search.setTo(DateMidnight.now().plusDays(1).toDate());
		Iterator<SureBet> sureBets = betService.findSureBets(search);
		List<SureBetDto> dtos = new LinkedList<BetRestService.SureBetDto>();
		while (sureBets.hasNext()) {
			SureBet sureBet = sureBets.next();
			SureBetDto dto = new SureBetDto();
			Collection<Bet> bets = sureBet.getBets();
			Map<String, Double> alternatives = new HashMap<String, Double>(
					bets.size());
			for (Bet bet : bets) {
				alternatives.put(bet.getBookMaker().getCode(), bet.getOdd());
				dto.odds = dto.odds + " / " + bet.getBookMaker().getCode()
						+ "=" + bet.getOdd();
			}
			dto.alternatives = alternatives;
			dto.odds = dto.odds.replaceAll("null", "");
			RefKey refKey = bets.iterator().next().getRefKey();
			dto.sport = refKey.getMatch().getSport().getCode();
			dto.match = refKey.getMatch().getCode();
			dto.betType = refKey.getBetType().getCode();
			dto.date = refKey.getMatch().getDate().toString();
			dto.profit = Math.floor((((1 - sureBet.getValue()) * 10000 + 0.5)))
					/ 100 + "%";
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Iterable<BetDto> getBets() {
		Iterable<Bet> bets = betRepository.findAll();
		return toDtos(bets);
	}

	protected Iterable<BetDto> toDtos(Iterable<Bet> bets) {
		return Iterables.transform(bets, new Function<Bet, BetDto>() {

			@Override
			@Nullable
			public BetDto apply(@Nullable Bet bet) {
				BetDto dto = new BetDto();
				dto.type = bet.getRefKey().getBetType().getCode();
				dto.alternative = bet.getCode();
				dto.bookmaker = bet.getBookMaker().getCode();
				dto.sport = bet.getRefKey().getMatch().getSport().getCode();
				dto.match = bet.getRefKey().getMatch().getCode();
				dto.matchDate = bet.getRefKey().getMatch().getDate();
				dto.value = bet.getOdd();
				dto.syncDate = bet.getDate();
				return dto;

				// type, bookmaker, match, alternative, sport;
			}
		});
	}

	@Override
	public Iterable<BetDto> search(fr.ele.model.search.BetSearch search) {
		QueryBuilder queryBuilder = new QueryBuilder();
		SearchMapping.map(queryBuilder, QBet.bet, search);
		Iterable<Bet> bets = betRepository.findAll(queryBuilder.build());
		Iterable<BetDto> dtos = toDtos(bets);
		return Lists.newLinkedList(dtos);
	}

}
