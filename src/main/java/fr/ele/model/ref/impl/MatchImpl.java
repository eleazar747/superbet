package fr.ele.model.ref.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ele.model.EntityImpl;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;

@Entity
@Table(name = "MATCH")
public class MatchImpl extends EntityImpl implements Match {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPORT_ID", nullable = false)
    private Sport sport;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DATE", nullable = false)
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
