package fr.ele.services.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvMarshaller;
import fr.ele.feeds.integration.AbstractSuperbetIntegrationTest;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.impl.BookMakerImpl;

public class BookMakerDaoTest extends AbstractSuperbetIntegrationTest {

    public static String newline = System.getProperty("line.separator");

    @Autowired
    private BookMakerDao bookMakerDao;

    @Test
    public void testFindByCode() {
        BookMaker bookMaker = new BookMakerImpl();
        String code = "BETCLICK";
        bookMaker.setCode(code);
        bookMakerDao.create(bookMaker);
        Assert.assertTrue(bookMaker.getId() > 0);
        Assert.assertEquals(code, bookMaker.getCode());
        BookMaker byCode = bookMakerDao.findByCode(code);
        Assert.assertNotNull(byCode);
        Assert.assertEquals(bookMaker.getId(), byCode.getId());
    }

    @Test
    public void test() throws IOException {
        BookMaker bookMaker = new BookMakerImpl();
        String code = "BETCLICK";
        bookMaker.setCode(code);
        bookMakerDao.create(bookMaker);
        Assert.assertTrue(bookMaker.getId() > 0);
        Assert.assertEquals(code, bookMaker.getCode());
        BookMaker byCode = bookMakerDao.findByCode(code);
        Assert.assertNotNull(byCode);
        Assert.assertEquals(bookMaker.getId(), byCode.getId());
        CsvContext<BookMaker> context = CsvContext.create(BookMaker.class);
        context.setWithHeader(false);
        CsvMarshaller<BookMaker> marshaller = context.newMarshaller();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshall(Collections.singletonList(byCode), outputStream);
        String csv = outputStream.toString();
        Assert.assertEquals("1,BETCLICK" + newline, csv);
    }

    @Test
    public void testFindAll() {
        BookMaker bookMaker = new BookMakerImpl();
        String code = "BETCLICK";
        bookMaker.setCode(code);
        bookMakerDao.create(bookMaker);
        bookMaker = new BookMakerImpl();
        bookMaker.setCode(code + "2");
        bookMakerDao.create(bookMaker);
        List<BookMaker> bookmarkers = bookMakerDao.findAll();
        Assert.assertNotNull(bookmarkers);
        Assert.assertEquals(2, bookmarkers.size());
    }

}
