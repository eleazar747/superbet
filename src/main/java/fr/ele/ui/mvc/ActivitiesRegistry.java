package fr.ele.ui.mvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

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
            Group group = groups.get(bean.getGroup());
            if (group == null) {
                group = new Group(bean.getGroup());
                groups.put(group.getName(), group);
            }
            group.add(new Activity(bean.getActivityName(), bean
                    .getActivityUrlBase()));
        }

    }

    public Collection<Group> getGroupActivities() {
        return groups.values();
    }
}
