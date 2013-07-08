package fr.ele.services.dao;

import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;

public interface MatchDao extends GenericDao<Match, QMatch> {
    Match findByCode(String code);
}
