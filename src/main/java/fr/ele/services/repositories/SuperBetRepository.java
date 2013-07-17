package fr.ele.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import fr.ele.model.SuperBetEntity;

@NoRepositoryBean
public interface SuperBetRepository<T extends SuperBetEntity> extends
        JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
