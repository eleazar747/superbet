package fr.ele.services.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.QSport;
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

    public List<Sport> findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        QSport sport = QSport.sport;
        return query.from(sport).where(sport.code.eq(code)).list(sport);
    }

}
