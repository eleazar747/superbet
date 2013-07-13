package fr.ele.services.dao;

import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.RefEntityType;

public interface DataMappingDao extends GenericDao<DataMapping, QDataMapping> {

    String getModelCode(RefEntityType type, String bookmakerCode);
}
