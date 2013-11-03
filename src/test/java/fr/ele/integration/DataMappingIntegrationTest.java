package fr.ele.integration;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvMarshaller;
import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;

public class DataMappingIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private DataMappingRepository dataMappingRepository;

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void testDao() {
        BookMaker bookMaker = bookMakerRepository.findByCode("betclic");
        DataMapping mapping = new DataMapping();
        mapping.setBookMakerCode("toto");
        mapping.setModelCode("tata");
        mapping.setBookMaker(bookMaker);
        mapping.setRefEntityType(RefEntityType.SPORT);
        dataMappingRepository.save(mapping);
        DataMapping result = dataMappingRepository
                .findOne(DataMappingRepository.Queries.findModelByBookMaker(
                        RefEntityType.SPORT, bookMaker, "toto"));
        Assert.assertNotNull(result);
        Assert.assertEquals("tata", result.getModelCode());
        CsvContext<DataMapping> csvContext = CsvContext.create(
                DataMapping.class, repositoryRegistry);
        CsvMarshaller<DataMapping> marshaller = csvContext.newMarshaller();
        marshaller.marshall(Collections.singletonList(result), System.out);
    }

    @Test
    public void testFind() {
        BookMaker bookMaker = bookMakerRepository.findByCode("betclic");
        DataMapping result = dataMappingRepository
                .findOne(DataMappingRepository.Queries.findModelByBookMaker(
                        RefEntityType.SPORT, bookMaker, "Football"));
        Assert.assertNotNull(result);
        Assert.assertEquals("Football", result.getModelCode());
    }

    @Test
    public void testFindAll() {
        List<DataMapping> results = Lists.newArrayList(dataMappingRepository
                .findAll());
        CsvContext<DataMapping> csvContext = CsvContext.create(
                DataMapping.class, repositoryRegistry);
        CsvMarshaller<DataMapping> marshaller = csvContext.newMarshaller();
        marshaller.marshall(results, System.out);
    }

}
