package fr.ele.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import fr.ele.model.ref.Sport;

public interface SportRepository extends JpaRepository<Sport, Long>,
        QueryDslPredicateExecutor<Sport> {

}
