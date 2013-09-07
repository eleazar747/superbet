package fr.ele.services.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import fr.ele.model.SuperBetEntity;

@NoRepositoryBean
public interface SuperBetRepository<T extends SuperBetEntity> extends
        PagingAndSortingRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
