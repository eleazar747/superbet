package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;
import fr.ele.services.dao.BetTypeDao;

@Repository
public class BetTypeDaoImpl extends GenericDaoImpl<BetType, QBetType> implements
        BetTypeDao {

    public BetTypeDaoImpl() {
        super(BetType.class, QBetType.betType);
    }

    @Override
    public BetType findByCode(String code) {
        JPAQuery query = new JPAQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
