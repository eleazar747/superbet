package fr.ele.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Entity {

    long getId();
}
