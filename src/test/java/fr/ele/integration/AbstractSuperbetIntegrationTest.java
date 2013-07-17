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
import fr.ele.core.jpa.HandledClass;
import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvUnmarshaller;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.SuperBetRepository;

@Ignore
@ContextConfiguration(locations = "/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
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

    private Map<Class<?>, SuperBetRepository> map;

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        ListableBeanFactory lf = (ListableBeanFactory) factory;
        Map<String, SuperBetRepository> beans = lf
                .getBeansOfType(SuperBetRepository.class);
        map = new HashMap<Class<?>, SuperBetRepository>(beans.size());
        for (SuperBetRepository bean : beans.values()) {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            for (Class<?> interfaze : interfaces) {
                HandledClass handledClass = interfaze
                        .getAnnotation(HandledClass.class);
                if (handledClass != null) {
                    map.put(handledClass.value(), bean);
                }
            }
        }
    }

    protected void initializeDatas() {
        for (Class clazz : REFS.keySet()) {
            CsvContext<? extends SuperBetEntity> context = CsvContext
                    .create(clazz);
            context.setWithHeader(true);
            CsvUnmarshaller<? extends SuperBetEntity> unmarshaller = context
                    .newUnmarshaller();
            Iterator iterator = unmarshaller
                    .unmarshall(AbstractSuperbetIntegrationTest.class
                            .getResourceAsStream(REFS.get(clazz)));
            while (iterator.hasNext()) {
                map.get(clazz).save(iterator.next());
            }
        }
    }
}
