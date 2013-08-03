package fr.ele.services.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.SuperBetEntity;

@Component
public class RepositoryRegistryImpl implements RepositoryRegistry,
        BeanFactoryAware {

    private Map<Class<SuperBetEntity>, SuperBetRepository<SuperBetEntity>> map;

    @Override
    public <T extends SuperBetEntity> SuperBetRepository<T> getRepository(
            Class<T> entityClass) {
        return (SuperBetRepository<T>) map.get(entityClass);
    }

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        ListableBeanFactory lf = (ListableBeanFactory) factory;
        Map<String, SuperBetRepository> beans = lf
                .getBeansOfType(SuperBetRepository.class);
        map = new HashMap<Class<SuperBetEntity>, SuperBetRepository<SuperBetEntity>>(
                beans.size());
        for (SuperBetRepository bean : beans.values()) {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            for (Class<?> interfaze : interfaces) {
                HandledClass handledClass = interfaze
                        .getAnnotation(HandledClass.class);
                if (handledClass != null) {
                    map.put((Class<SuperBetEntity>) handledClass.value(), bean);
                }
            }
        }
    }

}
