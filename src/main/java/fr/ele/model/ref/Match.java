package fr.ele.model.ref;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.MatchTable.TABLE)
@Proxy(proxyClass = Match.class)
public class Match extends HasCodeEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Sport.class)
    @JoinColumn(name = SuperBetTables.MatchTable.SPORT_COLUMN, nullable = false)
    private Sport sport;

    @Column(name = SuperBetTables.MatchTable.DATE_COLUMN, nullable = false)
    private Date date;

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
