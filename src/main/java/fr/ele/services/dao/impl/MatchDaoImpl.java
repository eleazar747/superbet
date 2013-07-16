package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;
import fr.ele.services.dao.MatchDao;

@Repository
public class MatchDaoImpl extends GenericDaoImpl<Match, QMatch> implements
        MatchDao {

    public MatchDaoImpl() {
        super(Match.class, QMatch.match);
    }

    @Override
    public Match findByCode(String code) {
        JPAQuery query = new JPAQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
