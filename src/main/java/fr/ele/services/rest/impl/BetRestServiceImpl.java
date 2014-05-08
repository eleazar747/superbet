package fr.ele.services.rest.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codahale.metrics.annotation.Timed;
import com.codiform.moo.curry.Translate;
import com.google.common.collect.Lists;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.dto.BetDto;
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
    @Timed
    public List<SureBetDto> getSureBets() {
        BetSearch search = new BetSearch();
        search.setFrom(DateMidnight.now().toDate());
        search.setTo(DateMidnight.now().plusDays(1).toDate());
        Iterator<SureBet> sureBets = betService.findSureBets(search);
        List<SureBetDto> dtos = new LinkedList<>();
        while (sureBets.hasNext()) {
            SureBet sureBet = sureBets.next();
            SureBetDto dto = new SureBetDto();
            Collection<Bet> bets = sureBet.getBets();
            Map<String, Double> alternatives = new HashMap<>(bets.size());
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
            dto.profit = Math.floor((1 - sureBet.getValue()) * 10000 + 0.5)
                    / 100 + "%";
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    @Timed
    public Iterable<BetDto> getBets() {
        Iterable<Bet> bets = betRepository.findAll();
        return Translate.to(BetDto.class).fromEach(Lists.newArrayList(bets));
    }

    @Override
    @Timed
    public Iterable<BetDto> search(fr.ele.model.search.BetSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, QBet.bet, search);
        Iterable<Bet> bets = betRepository.findAll(queryBuilder.build());
        return Translate.to(BetDto.class).fromEach(Lists.newArrayList(bets));
    }

}
