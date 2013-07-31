package fr.ele.ui;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.ext.Provider;

import fr.ele.ui.MetaTemplate.Dummy;
import fr.ele.ui.model.MetaRegistry;

@Provider
@MetaTemplate
public class MetaEntityToModelAndViewWrapper extends
        EntityToModelAndViewWrapper {

    @Inject
    private MetaRegistry metaRegistry;

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        Class<?> clazz = null;
        for (Annotation a : responseContext.getEntityAnnotations()) {
            if (a.annotationType() == MetaTemplate.class) {
                MetaTemplate metaTemplate = (MetaTemplate) a;
                if (Dummy.class.equals(metaTemplate.entityClass())) {
                    clazz = responseContext.getEntityClass();
                } else {
                    clazz = metaTemplate.entityClass();
                }

                StringBuilder sb = new StringBuilder();
                sb.append(metaRegistry.getMetaMapping(clazz).getIdentifier());
                sb.append(':');
                if (!metaTemplate.profile().isEmpty()) {
                    sb.append(metaTemplate.profile());
                    sb.append(':');
                }
                sb.append(metaTemplate.template());
                map(responseContext, sb.toString());
                break;
            }
        }
    }
}
