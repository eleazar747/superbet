package fr.ele.ui.mvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.github.mustachejava.Mustache;

import fr.ele.ui.mustache.MetaMustacheFactory;

public class MustacheViewResolver extends AbstractTemplateViewResolver
        implements InitializingBean {

    private MetaMustacheFactory compiler;

    private String resourceBase;

    private String resourceSuffix;

    public MustacheViewResolver() {
        setViewClass(MustacheView.class);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return MustacheView.class;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {

        final MustacheView view = (MustacheView) super.buildView(viewName);

        Mustache template = compiler.compile(viewName);
        view.setTemplate(template);

        return view;
    }

    public void afterPropertiesSet() throws Exception {
        compiler = new MetaMustacheFactory(resourceBase, resourceSuffix);
    }

    public void setResourceBase(String resourceBase) {
        this.resourceBase = resourceBase;
    }

    public void setResourceSuffix(String resourceSuffix) {
        this.resourceSuffix = resourceSuffix;
    }

}
