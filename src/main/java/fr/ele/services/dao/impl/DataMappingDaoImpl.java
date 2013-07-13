package fr.ele.services.dao.impl;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.impl.DataMappingImpl;
import fr.ele.services.dao.DataMappingDao;

public class DataMappingDaoImpl extends
        GenericDaoImpl<DataMapping, QDataMapping> implements DataMappingDao {

    public DataMappingDaoImpl() {
        super(DataMappingImpl.class, QDataMapping.dataMapping);
    }

    @Override
    public String getModelCode(RefEntityType type, String bookmakerCode) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        DataMapping dataMapping = query
                .from(entityQuery)
                .where(entityQuery.refEntityType.eq(type).and(
                        entityQuery.bookMakerCode.eq(bookmakerCode)))
                .uniqueResult(entityQuery);
        return dataMapping != null ? dataMapping.getModelCode() : null;
    }

}
