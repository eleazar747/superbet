package fr.ele.ui.mustache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class MetaMustacheFactory extends DefaultMustacheFactory {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(MetaMustacheFactory.class);

    private final String resourceBase;

    private final String resourceSuffix;

    public MetaMustacheFactory(String resourceBase, String resourceSuffix) {
        super();
        this.resourceBase = resourceBase;
        this.resourceSuffix = resourceSuffix;
    }

    @Override
    public Reader getReader(String template) {
        LOGGER.debug("meta {}", template);
        template = template.replace(".html", "");
        String path = resourceBase + template + resourceSuffix;
        LOGGER.debug("load mustache meta template : {} at {}", template, path);
        InputStream is = MetaMustacheFactory.class.getResourceAsStream(path);
        if (LOGGER.isDebugEnabled()) {
            String string = getStringFromInputStream(is);
            LOGGER.debug(string);
            return new StringReader(string);
        }
        return new BufferedReader(new InputStreamReader(is));
    }

    @Override
    protected LoadingCache<String, Mustache> createMustacheCache() {
        return CacheBuilder.newBuilder().build(
                new CacheLoader<String, Mustache>() {
                    @Override
                    public Mustache load(String key) throws Exception {
                        return mc.compile(getReader(key), key, "[[", "]]");
                    }
                });
    }

    @Override
    public Mustache compile(Reader reader, String name) {
        return compile(reader, name, "[[", "]]");
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
