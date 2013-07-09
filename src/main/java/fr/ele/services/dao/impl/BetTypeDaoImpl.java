package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;
import fr.ele.model.ref.impl.BetTypeImpl;
import fr.ele.services.dao.BetTypeDao;

@Repository
public class BetTypeDaoImpl extends GenericDaoImpl<BetType, QBetType> implements
        BetTypeDao {

    public BetTypeDaoImpl() {
        super(BetTypeImpl.class, QBetType.betType);
    }

    @Override
    public BetType findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
