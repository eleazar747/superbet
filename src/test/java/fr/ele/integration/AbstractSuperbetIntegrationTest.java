package fr.ele.integration;

import java.net.URL;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvUnmarshaller;
import fr.ele.model.ref.Sport;
import fr.ele.services.dao.SportDao;

@Ignore
@ContextConfiguration(locations = "/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public abstract class AbstractSuperbetIntegrationTest extends
        AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SportDao sportDao;

    protected void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void initializeDatas() {
        CsvContext<Sport> context = CsvContext.create(Sport.class);
        context.setWithHeader(true);
        CsvUnmarshaller<Sport> unmarshaller = context.newUnmarshaller();
        URL resource = AbstractSuperbetIntegrationTest.class
                .getResource("SportsRef.csv");
        System.err.println(resource);
        Iterator<Sport> iterator = unmarshaller
                .unmarshall(AbstractSuperbetIntegrationTest.class
                        .getResourceAsStream("SportsRef.csv"));
        while (iterator.hasNext()) {
            sportDao.create(iterator.next());
        }
    }
}
