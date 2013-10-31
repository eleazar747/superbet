package fr.ele.model.ref;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Proxy;

import com.mysema.query.annotations.QueryInit;

import fr.ele.model.SuperBetEntity;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.RefKeyTable.TABLE, uniqueConstraints = @UniqueConstraint(columnNames = {
        SuperBetTables.RefKeyTable.BETTYPE_ID_COLUMN,
        SuperBetTables.RefKeyTable.MATCH_ID_COLUMN}))
@Proxy(proxyClass = RefKey.class)
public class RefKey extends SuperBetEntity {
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BetType.class)
    @JoinColumn(name = SuperBetTables.RefKeyTable.BETTYPE_ID_COLUMN, nullable = false)
    @QueryInit("*")
    private BetType betType;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Match.class)
    @JoinColumn(name = SuperBetTables.RefKeyTable.MATCH_ID_COLUMN, nullable = false)
    @QueryInit("*")
    private Match match;

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
