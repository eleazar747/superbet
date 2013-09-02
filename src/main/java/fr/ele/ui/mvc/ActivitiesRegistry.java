package fr.ele.ui.mvc;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ActivitiesRegistry implements BeanFactoryAware {

    private Map<String, Group> groups;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ListableBeanFactory lf = (ListableBeanFactory) beanFactory;
        Map<String, ActivityController> beans = lf
                .getBeansOfType(ActivityController.class);
        groups = new HashMap();
        for (ActivityController bean : beans.values()) {
            Class<?> clazz = bean.getClass();
            fr.ele.ui.mvc.annotation.Group groupAnnotation = AnnotationUtils
                    .findAnnotation(clazz, fr.ele.ui.mvc.annotation.Group.class);
            String groupName = "";
            if (groupAnnotation != null) {
                groupName = groupAnnotation.value();
            }
            Group group = groups.get(groupName);
            if (group == null) {
                group = new Group(groupName);
                groups.put(group.getName(), group);
            }
            String baseUri = null;
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(
                    clazz, RequestMapping.class);
            if (requestMapping != null && requestMapping.value().length > 0) {
                baseUri = requestMapping.value()[0];
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                fr.ele.ui.mvc.annotation.Activity activity = AnnotationUtils
                        .findAnnotation(method,
                                fr.ele.ui.mvc.annotation.Activity.class);
                if (activity != null) {
                    requestMapping = AnnotationUtils.findAnnotation(method,
                            RequestMapping.class);
                    StringBuilder sb = new StringBuilder();
                    if (baseUri != null) {
                        sb.append(baseUri).append('/');
                    }
                    if (requestMapping != null
                            && requestMapping.value().length > 0) {
                        sb.append(requestMapping.value()[0]);
                    }
                    group.add(new Activity(activity.name(), sb.toString()));
                }
            }

        }

    }

    public Collection<Group> getGroupActivities() {
        return groups.values();
    }
}
