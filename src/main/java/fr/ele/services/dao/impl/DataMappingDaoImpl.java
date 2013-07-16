package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.services.dao.DataMappingDao;

@Repository
public class DataMappingDaoImpl extends
        GenericDaoImpl<DataMapping, QDataMapping> implements DataMappingDao {

    public DataMappingDaoImpl() {
        super(DataMapping.class, QDataMapping.dataMapping);
    }

    @Override
    public String getModelCode(RefEntityType type, String bookmakerCode) {
        JPAQuery query = new JPAQuery(getCurrentSession());
        DataMapping dataMapping = query
                .from(entityQuery)
                .where(entityQuery.refEntityType.eq(type).and(
                        entityQuery.bookMakerCode.eq(bookmakerCode)))
                .uniqueResult(entityQuery);
        return dataMapping != null ? dataMapping.getModelCode() : null;
    }

}
