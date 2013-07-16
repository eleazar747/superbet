package fr.ele.integration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.junit.Ignore;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.ele.core.ApplicationProfiles;
import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvUnmarshaller;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.dao.SportDao;

@Ignore
@ContextConfiguration(locations = "/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@ActiveProfiles(ApplicationProfiles.TEST)
public abstract class AbstractSuperbetIntegrationTest extends
        AbstractTransactionalJUnit4SpringContextTests implements
        BeanFactoryAware {

    private static final Map<Class<?>, String> REFS = new HashMap<Class<?>, String>();
    static {
        REFS.put(Sport.class, "SportsRef.csv");
        REFS.put(BetType.class, "BetTypesRef.csv");
        REFS.put(BookMaker.class, "BookMakersRef.csv");
    }

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    SportDao sportDao;

    private Map<Class<?>, GenericDao> map;

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        ListableBeanFactory lf = (ListableBeanFactory) factory;
        Map<String, GenericDao> beans = lf.getBeansOfType(GenericDao.class);
        map = new HashMap<Class<?>, GenericDao>(beans.size());
        for (GenericDao bean : beans.values()) {
            map.put(bean.handledClass(), bean);
        }
    }

    protected void initializeDatas() {
        for (Class clazz : REFS.keySet()) {
            CsvContext<? extends SuperBetEntity> context = CsvContext.create(clazz);
            context.setWithHeader(true);
            CsvUnmarshaller<? extends SuperBetEntity> unmarshaller = context
                    .newUnmarshaller();
            Iterator<? extends SuperBetEntity> iterator = unmarshaller
                    .unmarshall(AbstractSuperbetIntegrationTest.class
                            .getResourceAsStream(REFS.get(clazz)));
            while (iterator.hasNext()) {
                map.get(clazz).create(iterator.next());
            }
        }
    }
}
