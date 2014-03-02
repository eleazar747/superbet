package fr.ele.model.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ele.model.SuperBetEntity;

@Entity
@Table
public class Player extends SuperBetEntity {
    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Sport.class)
    @JoinColumn(nullable = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BookMaker.class)
    @JoinColumn(nullable = false)
    private BookMaker dataProvider;

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

    public BookMaker getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(BookMaker dataProvider) {
        this.dataProvider = dataProvider;
    }

}
