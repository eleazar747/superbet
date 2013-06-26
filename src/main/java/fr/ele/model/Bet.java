package fr.ele.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import fr.ele.model.ref.BookMarker;

@MappedSuperclass
public interface Bet extends Entity {

    double odd();

    Date getDate();

    BookMarker getBookMarker();
}
