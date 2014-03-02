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

import com.codiform.moo.curry.Translate;
import com.google.common.collect.Lists;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvUnmarshaller;
import fr.ele.core.csv.GraphResolver;
import fr.ele.csv.SuperBetGraphResolver;
import fr.ele.dto.SuperbetDto;
import fr.ele.model.SuperBetEntity;
import fr.ele.services.repositories.RepositoryRegistry;
import fr.ele.services.repositories.SuperBetRepository;

public abstract class AbstractBaseRestService<DTO extends SuperbetDto, MODEL extends SuperBetEntity> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryRegistry repositoryRegistry;

    protected Iterable<DTO> findAll() {
        LOGGER.debug("findAll");
        return Translate.to(dtoClass()).fromEach(
                Lists.newArrayList(getRepository().findAll()));
    }

    protected DTO get(long id) {
        LOGGER.debug("findById({})", id);
        return Translate.to(dtoClass()).from(getRepository().findOne(id));
    }

    protected DTO create(DTO dto) {
        LOGGER.debug("create({})", dto);
        MODEL model = Translate.to(modelClass()).from(dto);
        getRepository().save(model);
        return Translate.to(dtoClass()).from(model);
    }

    protected void delete(long id) {
        LOGGER.debug("delete({})", id);
        getRepository().delete(id);
    }

    protected List<DTO> insertCsv(Attachment file, Class<MODEL> clazz) {
        try {
            InputStream in = file.getDataHandler().getInputStream();
            GraphResolver graphResolver = new SuperBetGraphResolver(
                    repositoryRegistry);
            CsvContext<MODEL> context = CsvContext.create(clazz, graphResolver);
            CsvUnmarshaller<MODEL> unmarshaller = context.newUnmarshaller();
            Iterator<MODEL> iterator = unmarshaller.unmarshall(in);
            List<MODEL> results = new LinkedList<MODEL>();
            while (iterator.hasNext()) {
                MODEL bookMaker = getRepository().save(iterator.next());
                results.add(bookMaker);
            }
            return Translate.to(dtoClass()).fromEach(results);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract SuperBetRepository<MODEL> getRepository();

    protected abstract Class<MODEL> modelClass();

    protected abstract Class<DTO> dtoClass();
}
