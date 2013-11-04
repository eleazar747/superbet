package fr.ele.core.csv;

import fr.ele.services.repositories.RepositoryRegistry;

public interface GraphResolver extends RepositoryRegistry {
    <T> T findByCode(Class<T> clazz, String code);

    <T> T findById(Class<T> clazz, Long id);
}
