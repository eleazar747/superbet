package fr.ele.services.bets.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysema.query.types.expr.BooleanExpression;

import fr.ele.model.Bet;
import fr.ele.model.QBet;
import fr.ele.services.bets.BetQueryService;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.repositories.BetRepository;

@Service
public class BetQueryServiceImpl implements BetQueryService {
    @Autowired
    private BetRepository betRepository;

    @Override
    public Iterator<Bet> iterate(BetSearch search) {
        QBet qBet = QBet.bet;
        BooleanExpression query = qBet.date.between(search.getFrom(),
                search.getTo());
        return betRepository.findAll(query).iterator();
    }

}
