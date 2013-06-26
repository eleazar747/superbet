package fr.ele.services.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.services.dao.SportDao;

@Repository
public class SportDaoImpl extends GenericDaoImpl<Sport> implements SportDao {

    public SportDaoImpl() {
        super(Sport.class);
    }

    public List<Sport> findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        QSport sport = QSport.sport;
        return query.from(sport).where(sport.code.eq(code)).list(sport);
    }

}
