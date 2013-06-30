package fr.ele.model.ref.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.EntityImpl;
import fr.ele.model.SuperBetTables;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;

@Entity
@Table(name = SuperBetTables.MatchTable.TABLE)
@Proxy(proxyClass = Match.class)
public class MatchImpl extends EntityImpl implements Match {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SportImpl.class)
    @JoinColumn(name = SuperBetTables.MatchTable.SPORT_COLUMN, nullable = false)
    private Sport sport;

    @Column(name = SuperBetTables.MatchTable.CODE_COLUMN, nullable = false, unique = true)
    private String code;

    @Column(name = SuperBetTables.MatchTable.DATE_COLUMN, nullable = false)
    private Date date;

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
