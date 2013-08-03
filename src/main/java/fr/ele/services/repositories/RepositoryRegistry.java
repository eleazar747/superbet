package fr.ele.services.repositories;

import fr.ele.model.SuperBetEntity;

public interface RepositoryRegistry {
    <T extends SuperBetEntity> SuperBetRepository<T> getRepository(
            Class<T> entityClass);
}
