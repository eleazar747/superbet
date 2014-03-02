package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codahale.metrics.annotation.Timed;
import com.codiform.moo.curry.Translate;
import com.google.common.collect.Lists;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.dto.DataMappingDto;
import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.search.DataMappingSearch;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.DataMappingRestService;

@Service(DataMappingRestService.SERVER)
@Transactional(readOnly = true)
public class DataMappingRestServiceImpl extends
        AbstractBaseRestService<DataMappingDto, DataMapping> implements
        DataMappingRestService {

    @Autowired
    private DataMappingRepository dataMappingRepository;

    @Override
    protected SuperBetRepository<DataMapping> getRepository() {
        return dataMappingRepository;
    }

    @Override
    @Timed
    public Iterable<DataMappingDto> findAll() {
        return super.findAll();
    }

    @Timed
    public Iterable<DataMappingDto> search(DataMappingSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, QDataMapping.dataMapping, search);
        Iterable<DataMapping> models = dataMappingRepository
                .findAll(queryBuilder.build());
        return Translate.to(dtoClass()).fromEach(Lists.newArrayList(models));
    }

    @Override
    @Timed
    public DataMappingDto get(long id) {
        return super.get(id);
    }

    @Override
    protected Class<DataMapping> modelClass() {
        return DataMapping.class;
    }

    @Override
    protected Class<DataMappingDto> dtoClass() {
        return DataMappingDto.class;
    }

    @Override
    @Timed
    @Transactional
    public DataMappingDto create(DataMappingDto model) {
        return super.create(model);
    }

    @Override
    @Timed
    @Transactional
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    @Timed
    @Transactional
    public List<DataMappingDto> insertCsv(Attachment file) {
        return insertCsv(file, DataMapping.class);
    }
}
