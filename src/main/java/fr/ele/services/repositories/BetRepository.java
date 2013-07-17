package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.Bet;

@HandledClass(Bet.class)
public interface BetRepository extends SuperBetRepository<Bet> {

}
