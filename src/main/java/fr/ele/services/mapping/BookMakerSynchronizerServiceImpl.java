package fr.ele.services.mapping;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.ref.BookMaker;

@Service
public class BookMakerSynchronizerServiceImpl implements
        BookMakerSynchronizerService, BeanFactoryAware {

    private Map<String, SynchronizerService> synchronizers;

    @Override
    public BookMakerSynchronization synchronize(BookMaker bookMaker) {
        SynchronizerService service = synchronizers.get(bookMaker
                .getSynchronizerService());
        if (service == null) {
            throw new RuntimeException(String.format(
                    "Sync service %s not found for bookmaker %s",
                    bookMaker.getSynchronizerService(), bookMaker.getCode()));
        }
        String urlSync = bookMaker.getUrlSync();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlSync);
            HttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            Object dto = service
                    .unmarshall(new BufferedInputStream(inputStream));
            return service.synchronize(bookMaker.getCode(), dto);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ListableBeanFactory lf = (ListableBeanFactory) beanFactory;
        synchronizers = lf.getBeansOfType(SynchronizerService.class);
    }

}
