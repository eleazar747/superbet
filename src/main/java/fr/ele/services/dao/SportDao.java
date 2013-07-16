package fr.ele.services.dao;

import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;

public interface SportDao extends GenericDao<Sport, QSport> {
    Sport findByCode(String code);
}
