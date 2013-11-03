package fr.ele.services.rest.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvUnmarshaller;
import fr.ele.model.SuperBetEntity;
import fr.ele.services.repositories.RepositoryRegistry;
import fr.ele.services.repositories.SuperBetRepository;

public abstract class AbstractBaseRestService<T extends SuperBetEntity> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryRegistry repositoryRegistry;

    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        LOGGER.debug("findAll");
        return getRepository().findAll();
    }

    @Transactional(readOnly = true)
    public T get(long id) {
        LOGGER.debug("getById(id={})", id);
        return getRepository().findOne(id);
    }

    @Transactional
    protected T create(T model) {
        getRepository().save(model);
        return model;
    }

    @Transactional
    protected void delete(long id) {
        getRepository().delete(id);
    }

    @Transactional
    public List<T> insertCsv(Attachment file, Class<T> clazz) {
        try {
            InputStream in = file.getDataHandler().getInputStream();
            CsvContext<T> context = CsvContext
                    .create(clazz, repositoryRegistry);
            CsvUnmarshaller<T> unmarshaller = context.newUnmarshaller();
            Iterator<T> iterator = unmarshaller.unmarshall(in);
            List<T> results = new LinkedList<T>();
            while (iterator.hasNext()) {
                T bookMaker = getRepository().save(iterator.next());
                results.add(bookMaker);
            }
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract SuperBetRepository<T> getRepository();

}
