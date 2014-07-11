package fr.ele.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.herman.metatype.annotation.MetaBean;

@MetaBean
@Entity
@Table
public class UnMatchedPlayer extends SuperBetEntity {
    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Sport.class)
    @JoinColumn(nullable = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = BookMaker.class)
    @JoinColumn(nullable = false)
    private BookMaker bookMaker;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public BookMaker getBookMaker() {
        return bookMaker;
    }

    public void setBookMaker(BookMaker bookMaker) {
        this.bookMaker = bookMaker;
    }

}
