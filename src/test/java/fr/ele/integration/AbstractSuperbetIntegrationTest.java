package fr.ele.integration;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.ele.core.ApplicationProfiles;
import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvUnmarshaller;
import fr.ele.core.csv.GraphResolver;
import fr.ele.csv.SuperBetGraphResolver;
import fr.ele.model.DataMapping;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.RepositoryRegistry;

@Ignore
@ContextConfiguration(locations = "/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles(ApplicationProfiles.TEST)
public abstract class AbstractSuperbetIntegrationTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    private static final Map<Class<?>, String> REFS = new LinkedHashMap<Class<?>, String>();
    static {
        REFS.put(Sport.class, "SportsRef.csv");
        REFS.put(BetType.class, "BetTypesRef.csv");
        REFS.put(BookMaker.class, "BookMakersRef.csv");
        REFS.put(DataMapping.class, "DataMappingsRef.csv");
    }

    @Autowired
    RepositoryRegistry repositoryRegistry;

    protected void initializeDatas() {
        for (Class clazz : REFS.keySet()) {
            GraphResolver graphResolver = new SuperBetGraphResolver(
                    repositoryRegistry);
            CsvContext<? extends SuperBetEntity> context = CsvContext.create(
                    clazz, graphResolver);
            context.setWithHeader(true);
            CsvUnmarshaller<? extends SuperBetEntity> unmarshaller = context
                    .newUnmarshaller();
            Iterator iterator = unmarshaller
                    .unmarshall(AbstractSuperbetIntegrationTest.class
                            .getResourceAsStream(REFS.get(clazz)));
            while (iterator.hasNext()) {
                repositoryRegistry.getRepository(clazz).save(iterator.next());
            }
        }
    }
}
