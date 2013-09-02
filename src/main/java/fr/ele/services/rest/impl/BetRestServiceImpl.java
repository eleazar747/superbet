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

import fr.ele.model.Bet;
import fr.ele.model.ref.RefKey;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.bets.BetService;
import fr.ele.services.bets.SureBet;
import fr.ele.services.rest.BetRestService;

@Service(BetRestService.SERVER)
public class BetRestServiceImpl implements BetRestService {

    @Autowired
    private BetService betService;

    @Override
    @Transactional(readOnly = true)
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
            }
            dto.alternatives = alternatives;
            RefKey refKey = bets.iterator().next().getRefKey();
            dto.sport = refKey.getMatch().getSport().getCode();
            dto.match = refKey.getMatch().getCode();
            dto.betType = refKey.getBetType().getCode();
            dtos.add(dto);
        }
        return dtos;
    }

}
