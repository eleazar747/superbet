package fr.ele.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Charsets;

public class MustacheTemplateEngine implements TemplateEngine {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(MustacheTemplateEngine.class);

    private final MustacheFactory factory = new DefaultMustacheFactory() {

        @Override
        public Reader getReader(String template) {
            String path = "/mustache/" + template;
            LOGGER.info("load mustache template : {} at {}", template, path);
            InputStream is = MustacheTemplateEngine.class
                    .getResourceAsStream(path);
            return new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
        }
    };

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
