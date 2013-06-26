package fr.ele.model.ref;

import javax.persistence.MappedSuperclass;

import fr.ele.model.util.Entity;

@MappedSuperclass
public interface BookMarker extends Entity {

    String getName();

}
