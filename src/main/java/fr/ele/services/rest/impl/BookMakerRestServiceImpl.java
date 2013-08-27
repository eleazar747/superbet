package fr.ele.services.rest.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvUnmarshaller;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.RepositoryRegistry;
import fr.ele.services.rest.BookMakerRestService;

@Transactional
@Service(BookMakerRestService.SERVER)
public class BookMakerRestServiceImpl extends
        AbstractRefRestServiceImpl<BookMaker> implements BookMakerRestService {

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Autowired
    private RepositoryRegistry repositoryRegistry;

    @Override
    protected BookMakerRepository getRepository() {
        return bookMakerRepository;
    }

    @Override
    public List<BookMaker> findAll() {
        return super.findAll();
    }

    @Override
    protected QBookMaker entityPath() {
        return QBookMaker.bookMaker;
    }

    @Override
    public BookMaker create(BookMaker bookmaker) {
        return super.create(bookmaker);
    }

    @Override
    @Transactional
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    public List<BookMaker> insertCsv(Attachment file) {
        try {
            InputStream in = file.getDataHandler().getInputStream();
            CsvContext<BookMaker> context = CsvContext.create(BookMaker.class,
                    repositoryRegistry);
            CsvUnmarshaller<BookMaker> unmarshaller = context.newUnmarshaller();
            Iterator<BookMaker> iterator = unmarshaller.unmarshall(in);
            List<BookMaker> results = new LinkedList<BookMaker>();
            while (iterator.hasNext()) {
                BookMaker bookMaker = bookMakerRepository.save(iterator.next());
                results.add(bookMaker);
            }
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
