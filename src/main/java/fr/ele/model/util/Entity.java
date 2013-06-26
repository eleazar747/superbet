package fr.ele.model.util;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Entity {

    long getId();
}
