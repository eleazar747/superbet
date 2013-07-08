package fr.ele.services.dao;

import fr.ele.model.ref.Sport;
import fr.ele.model.ref.impl.QSportImpl;

public interface SportDao extends GenericDao<Sport, QSportImpl> {
    Sport findByCode(String code);
}
