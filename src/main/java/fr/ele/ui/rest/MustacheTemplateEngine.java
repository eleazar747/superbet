package fr.ele.ui.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import fr.ele.ui.model.MetaRegistry;
import fr.ele.ui.mustache.MetaMustacheFactory;

public class MustacheTemplateEngine implements TemplateEngine {

    private class SuperBetMustacheFactory extends DefaultMustacheFactory {
        @Override
        public Reader getReader(String template) {
            LOGGER.info(template);
            if (template.contains(":")) {
                String[] split = template.split(":");
                String template2 = split[split.length - 1];
                LOGGER.debug("Compile {}", template);
                Mustache meta = metaFactory
                        .compile(metaFactory.getReader(template2), template2,
                                "[[", "]]");
                StringWriter sw = new StringWriter();
                LOGGER.debug("Execute {}", template);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("model", metaRegistry.getMetaMapping(split[0]));
                meta.execute(sw, map);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(sw.toString());
                }
                return new StringReader(sw.toString());
            }
            String path = "/mustache/" + template + ".mustache";
            LOGGER.info("load mustache template : {} at {}", template, path);
            InputStream is = MustacheTemplateEngine.class
                    .getResourceAsStream(path);
            if (LOGGER.isDebugEnabled()) {
                String string = getStringFromInputStream(is);
                LOGGER.info(string);
                return new StringReader(string);
            }
            return new BufferedReader(new InputStreamReader(is));
        }
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

    private final static Logger LOGGER = LoggerFactory
            .getLogger(MustacheTemplateEngine.class);

    @Inject
    private MetaRegistry metaRegistry;

    private final MetaMustacheFactory metaFactory = new MetaMustacheFactory(
            "/mustache/", ".mustache");

    private final SuperBetMustacheFactory factory = new SuperBetMustacheFactory();

    @Override
    public void execute(OutputStream outputStream, String view,
            Map<String, Object> datas) {
        try {
            Mustache mustache = factory.compile(view);
            Writer writer = new OutputStreamWriter(outputStream);
            mustache.execute(writer, datas);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
