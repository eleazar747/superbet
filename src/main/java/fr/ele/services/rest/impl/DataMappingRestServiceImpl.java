package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.model.DataMapping;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.SuperBetRepository;
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
    public List<DataMapping> findAll() {
        return super.findAll();
    }

}