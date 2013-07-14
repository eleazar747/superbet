package fr.ele.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.RefKey;

@MappedSuperclass
public interface Bet extends Entity {

    double getOdd();

    Date getDate();

    BookMaker getBookMaker();

    RefKey getRefKey();

    void setOdd(double odd);

    void setDate(Date date);

    void setRefKey(RefKey refKey);

    void setBookMaker(BookMaker bookMaker);
}
