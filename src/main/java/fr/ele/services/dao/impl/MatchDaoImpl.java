package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;
import fr.ele.model.ref.impl.MatchImpl;
import fr.ele.services.dao.MatchDao;

@Repository
public class MatchDaoImpl extends GenericDaoImpl<Match, QMatch> implements
        MatchDao {

    public MatchDaoImpl() {
        super(MatchImpl.class, QMatch.match);
    }

    @Override
    public Match findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
