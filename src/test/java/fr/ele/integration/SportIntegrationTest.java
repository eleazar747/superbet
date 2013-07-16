package fr.ele.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.SportRepository;

public class SportIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private SportRepository sportRepository;

    @Test
    public void testCrud() {
        Sport sport = new Sport();
        sport.setCode("toto");
        sportRepository.save(sport);
        Sport sport2 = sportRepository.findOne(sport.getId());
        Assert.assertEquals(sport.getCode(), sport2.getCode());
        sportRepository.findOne(QSport.sport.code.eq("toto"));
    }
}
