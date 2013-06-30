package fr.ele.services.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.integration.AbstractSuperbetIntegrationTest;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.impl.BookMakerImpl;

public class BookMakerDaoTest extends AbstractSuperbetIntegrationTest {
	
	@Autowired
	private BookMakerDao bookMakerDao;
	
	@Test
	public void testFindByCode() {
		BookMaker bookMaker = new BookMakerImpl();
		String code = "BETCLICK";
		bookMaker.setCode(code);
		bookMakerDao.create(bookMaker);
		Assert.assertTrue(bookMaker.getId()>0);
		Assert.assertEquals(code, bookMaker.getCode());
		BookMaker byCode = bookMakerDao.findByCode(code);
		Assert.assertNotNull(byCode);
		Assert.assertEquals(bookMaker.getId(), byCode.getId());
	}
}
