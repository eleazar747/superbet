package fr.ele.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        BookMaker bookMaker = bookMakerRepository.findByCode("betclick");
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

    }
}
