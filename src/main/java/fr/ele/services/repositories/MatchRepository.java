package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.Match;

@HandledClass(Match.class)
public interface MatchRepository extends SuperBetRepository<Match>,
        HasCodeRepository<Match> {

}
