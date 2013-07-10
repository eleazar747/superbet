package fr.ele.integration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import fr.ele.model.Entity;
import fr.ele.model.ref.impl.BetTypeImpl;
import fr.ele.model.ref.impl.BookMakerImpl;
import fr.ele.model.ref.impl.SportImpl;
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
        REFS.put(SportImpl.class, "SportsRef.csv");
        REFS.put(BetTypeImpl.class, "BetTypesRef.csv");
        REFS.put(BookMakerImpl.class, "BookMakersRef.csv");
    }

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SportDao sportDao;

    private Map<Class<?>, GenericDao> map;

    protected void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

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
            CsvContext<? extends Entity> context = CsvContext.create(clazz);
            context.setWithHeader(true);
            CsvUnmarshaller<? extends Entity> unmarshaller = context
                    .newUnmarshaller();
            Iterator<? extends Entity> iterator = unmarshaller
                    .unmarshall(AbstractSuperbetIntegrationTest.class
                            .getResourceAsStream(REFS.get(clazz)));
            while (iterator.hasNext()) {
                map.get(clazz).create(iterator.next());
            }
        }
    }
}
