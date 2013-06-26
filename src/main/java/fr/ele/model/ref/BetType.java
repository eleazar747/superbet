package fr.ele.model.ref;

import javax.persistence.MappedSuperclass;

import fr.ele.model.util.Entity;

@MappedSuperclass
public interface BetType extends Entity {

    String getName();
}
