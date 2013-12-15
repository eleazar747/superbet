package fr.ele.integration;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.core.encoding.EncodingFinder;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.repositories.BookMakerRepository;

public class EncodingTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void testEncoding() throws Throwable {
        BookMaker bookMaker = bookMakerRepository.findByCode("ESPN");
        HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(bookMaker.getUrlSync());

        HttpResponse response = client.execute(request);
        InputStream inputStream = response.getEntity().getContent();
        EncodingFinder encodingFinder = new EncodingFinder();
        String encoding = encodingFinder.findEncoding(inputStream);
        System.err.println(encoding);
        inputStream.close();
    }
}
