package fr.ele.integration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvMarshaller;
import fr.ele.core.csv.CsvUnmarshaller;
import fr.ele.core.csv.GraphResolver;
import fr.ele.core.search.criteria.string.StringOperator;
import fr.ele.core.search.criteria.string.StringValueCriteria;
import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.csv.SuperBetGraphResolver;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.services.repositories.BookMakerRepository;

public class BookMakerDaoTest extends AbstractSuperbetIntegrationTest {

    public static String newline = System.getProperty("line.separator");

    private static final String BOOKMAKER = "BETCLIC";

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void testFindByCode() {
        BookMaker bookMaker = new BookMaker();
        bookMaker.setCode(BOOKMAKER);
        bookMakerRepository.save(bookMaker);
        Assert.assertTrue(bookMaker.getId() > 0);
        Assert.assertEquals(BOOKMAKER, bookMaker.getCode());
        BookMaker byCode = bookMakerRepository.findByCode(BOOKMAKER);
        Assert.assertNotNull(byCode);
        Assert.assertEquals(bookMaker.getId(), byCode.getId());
    }

    @Test
    public void testCsv() {
        BookMaker bookMaker = new BookMaker();
        bookMaker.setCode(BOOKMAKER);
        bookMakerRepository.save(bookMaker);
        Assert.assertTrue(bookMaker.getId() > 0);
        Assert.assertEquals(BOOKMAKER, bookMaker.getCode());
        BookMaker byCode = bookMakerRepository.findByCode(BOOKMAKER);
        Assert.assertNotNull(byCode);
        Assert.assertEquals(bookMaker.getId(), byCode.getId());
        GraphResolver graphResolver = new SuperBetGraphResolver(
                repositoryRegistry);
        CsvContext<BookMaker> context = CsvContext.create(BookMaker.class,
                graphResolver);
        context.setWithHeader(false);
        CsvMarshaller<BookMaker> marshaller = context.newMarshaller();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshall(Collections.singletonList(byCode), outputStream);
        String csv = outputStream.toString();
        Assert.assertNotNull(csv);
        Assert.assertTrue(!csv.isEmpty());
        Assert.assertTrue(csv.contains("," + BOOKMAKER));
        CsvUnmarshaller<BookMaker> unmarshaller = context.newUnmarshaller();
        Iterator<BookMaker> it = unmarshaller
                .unmarshall(new ByteArrayInputStream(csv.getBytes()));
        Assert.assertTrue(it.hasNext());
        BookMaker unmarshalledBookmaker = it.next();
        Assert.assertNotNull(unmarshalledBookmaker);
        Assert.assertEquals(bookMaker.getId(), unmarshalledBookmaker.getId());
        Assert.assertEquals(bookMaker.getCode(),
                unmarshalledBookmaker.getCode());
    }

    @Test
    public void testFindAll() {
        List<BookMaker> bookmarkers = bookMakerRepository.findAll();
        Assert.assertNotNull(bookmarkers);
        Assert.assertEquals(4, bookmarkers.size());
        BookMaker bookMaker = new BookMaker();
        bookMaker.setCode(BOOKMAKER);
        bookMakerRepository.save(bookMaker);
        bookMaker = new BookMaker();
        bookMaker.setCode(BOOKMAKER + "2");
        bookMakerRepository.save(bookMaker);
        List<BookMaker> bookmarkersAfter = bookMakerRepository.findAll();
        Assert.assertNotNull(bookmarkersAfter);
        Assert.assertEquals(bookmarkers.size() + 2, bookmarkersAfter.size());
    }

    @Test
    public void testSearch() {
        BookmakerSearch search = new BookmakerSearch();
        StringValueCriteria code = new StringValueCriteria();
        code.setOperator(StringOperator.START_WITH);
        code.setCriteriaValue("bet");
        search.setCode(code);
        Iterable<BookMaker> iterable = bookMakerRepository
                .findAll(new QueryBuilder().and(QBookMaker.bookMaker.code,
                        search.getCode()).build());
        Iterator<BookMaker> it = iterable.iterator();
        Assert.assertTrue(it.hasNext());
        it.next();
        Assert.assertFalse(it.hasNext());
    }
}
