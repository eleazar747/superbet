package fr.ele.model.ref;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import fr.ele.model.util.Entity;

@MappedSuperclass
public interface Match extends Entity {

    Sport getSport();

    String getCode();

    Date getDate();
}
