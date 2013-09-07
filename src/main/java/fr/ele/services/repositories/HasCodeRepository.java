package fr.ele.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import fr.ele.model.HasCodeEntity;

@NoRepositoryBean
public interface HasCodeRepository<T extends HasCodeEntity> extends
        JpaRepository<T, Long> {
    T findByCode(String code);
}
