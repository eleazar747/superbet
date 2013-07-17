package fr.ele.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.services.repositories.DataMappingRepository;

public class DataMappingIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private DataMappingRepository dataMappingRepository;

    @Test
    public void testDao() {
        DataMapping mapping = new DataMapping();
        mapping.setBookMakerCode("toto");
        mapping.setModelCode("tata");
        mapping.setRefEntityType(RefEntityType.SPORT);
        dataMappingRepository.save(mapping);
        DataMapping result = dataMappingRepository
                .findModelCodeByRefEntityTypeAndBookMakerCode(
                        RefEntityType.SPORT, "toto");
        Assert.assertNotNull(result);
        Assert.assertEquals("tata", result.getModelCode());

    }
}
