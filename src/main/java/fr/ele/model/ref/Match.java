package fr.ele.model.ref;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import fr.ele.model.HasCodeEntity;

@MappedSuperclass
public interface Match extends HasCodeEntity {

    Sport getSport();

    Date getDate();
}
