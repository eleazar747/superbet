package fr.ele.services.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import fr.ele.model.HasCodeEntity;

@NoRepositoryBean
public interface HasCodeRepository<T extends HasCodeEntity> {
    T findByCode(String code);
}
