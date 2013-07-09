package fr.ele.services.dao;

import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;

public interface BetTypeDao extends GenericDao<BetType, QBetType> {
    BetType findByCode(String code);
}
