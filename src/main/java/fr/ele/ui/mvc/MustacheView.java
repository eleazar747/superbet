package fr.ele.ui.mvc;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.github.mustachejava.Mustache;

public class MustacheView extends AbstractTemplateView {

    private Mustache template;

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType(getContentType());
        final Writer writer = response.getWriter();
        template.execute(writer, model);
        writer.flush();
    }

    public void setTemplate(Mustache template) {
        this.template = template;
    }

    public Mustache getTemplate() {
        return template;
    }
}
