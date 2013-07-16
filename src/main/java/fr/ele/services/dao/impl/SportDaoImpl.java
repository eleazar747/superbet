package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.services.dao.SportDao;

@Repository
public class SportDaoImpl extends GenericDaoImpl<Sport, QSport> implements
        SportDao {

    public SportDaoImpl() {
        super(Sport.class, QSport.sport);
    }

    @Override
    public Sport findByCode(String code) {
        JPAQuery query = new JPAQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
