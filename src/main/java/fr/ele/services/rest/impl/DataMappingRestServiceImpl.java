package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.search.DataMappingSearch;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.DataMappingRestService;

@Service(DataMappingRestService.SERVER)
public class DataMappingRestServiceImpl extends
        AbstractBaseRestService<DataMapping> implements DataMappingRestService {

    @Autowired
    private DataMappingRepository dataMappingRepository;

    @Override
    protected SuperBetRepository<DataMapping> getRepository() {
        return dataMappingRepository;
    }

    @Override
    public Iterable<DataMapping> findAll() {
        return super.findAll();
    }

    public Iterable<DataMapping> search(DataMappingSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, QDataMapping.dataMapping, search);
        return dataMappingRepository.findAll(queryBuilder.build());
    }

    @Override
    public DataMapping create(DataMapping model) {
        return super.create(model);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public List<DataMapping> insertCsv(Attachment file) {
        return insertCsv(file, DataMapping.class);
    }
}
