package fr.ele.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ele.model.ref.BookMaker;

@Entity
@Table
public class BookMakerSynchronization extends SuperBetEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookMaker bookMaker;

    @Column(nullable = false)
    private Date synchronizationDate;

    @Column
    private Long nbBets;

    @Column
    private Long duration;

    public BookMaker getBookMaker() {
        return bookMaker;
    }

    public void setBookMaker(BookMaker bookMaker) {
        this.bookMaker = bookMaker;
    }

    public Date getSynchronizationDate() {
        return synchronizationDate;
    }

    public void setSynchronizationDate(Date synchronizationDate) {
        this.synchronizationDate = synchronizationDate;
    }

    public Long getNbBets() {
        return nbBets;
    }

    public void setNbBets(Long nbBets) {
        this.nbBets = nbBets;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
