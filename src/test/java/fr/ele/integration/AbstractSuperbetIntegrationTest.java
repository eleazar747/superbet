package fr.ele.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@Ignore
@ContextConfiguration(locations = "/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", 
                                     defaultRollback = true) 
public abstract class AbstractSuperbetIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
    SessionFactory sessionFactory;

    protected void flush() {
          this.sessionFactory.getCurrentSession().flush();
    }

    protected Session getCurrentSession() {
          return sessionFactory.getCurrentSession();
    }
}
