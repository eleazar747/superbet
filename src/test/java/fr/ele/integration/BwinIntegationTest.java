package fr.ele.integration;

import java.io.BufferedInputStream;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.bwin.dto.ROOT;
import fr.ele.services.mapping.BwinSynchroniser;

public class BwinIntegationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private BwinSynchroniser bwinSynchroniser;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() throws Throwable {
        BufferedInputStream inputStream = new BufferedInputStream(
                BwinIntegationTest.class
                        .getResourceAsStream("/fr/ele/feeds/bwin/bwin.xml"));
        ROOT root = bwinSynchroniser.unmarshall(inputStream);
        Assert.assertNotNull(root);
        Assert.assertNotNull(root.getROOT());

        bwinSynchroniser.synchronize("bwin", root);
    }

}
