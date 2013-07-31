package fr.ele.ui;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@Template
public class EntityToModelAndViewWrapper implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        for (Annotation a : responseContext.getEntityAnnotations()) {
            if (a.annotationType() == Template.class) {
                String templatePath = ((Template) a).value();
                map(responseContext, templatePath);
                break;
            }
        }
    }

    protected void map(ContainerResponseContext responseContext,
            String templatePath) {
        ModelAndView mav = new ModelAndView(responseContext.getEntity(),
                templatePath);
        responseContext.setEntity(mav, responseContext.getEntityAnnotations(),
                responseContext.getMediaType());
    }
}
