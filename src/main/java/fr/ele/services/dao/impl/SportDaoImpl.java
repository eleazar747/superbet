package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.Sport;
import fr.ele.model.ref.impl.QSportImpl;
import fr.ele.model.ref.impl.SportImpl;
import fr.ele.services.dao.SportDao;

@Repository
public class SportDaoImpl extends GenericDaoImpl<Sport, QSportImpl> implements
        SportDao {

    public SportDaoImpl() {
        super(SportImpl.class, QSportImpl.sportImpl);
    }

    @Override
    public Sport findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
