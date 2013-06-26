package fr.ele.services.impl;

import org.springframework.stereotype.Repository;

import fr.ele.model.ref.Sport;
import fr.ele.services.SportDao;

@Repository
public class SportDaoImpl extends GenericDaoImpl<Sport> implements SportDao {

    public SportDaoImpl() {
        super(Sport.class);
    }

}
