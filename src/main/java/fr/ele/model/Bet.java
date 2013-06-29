package fr.ele.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.RefKey;

@MappedSuperclass
public interface Bet extends Entity {

    double getOdd();

    Date getDate();

    BookMaker getBookMarker();
    
    RefKey getRefKey();
}
