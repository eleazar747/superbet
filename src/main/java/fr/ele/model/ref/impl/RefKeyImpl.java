package fr.ele.model.ref.impl;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.EntityImpl;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;

@Entity
@Table(name = "REF_KEY")
@Proxy(proxyClass=RefKey.class)
public class RefKeyImpl extends EntityImpl implements RefKey {
    @ManyToOne(fetch = FetchType.LAZY,targetEntity=BetTypeImpl.class)
    @JoinColumn(name = "BET_TYPE_ID", nullable = false)
    private BetType betType;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity=MatchImpl.class)
    @JoinColumn(name = "MATCH_ID", nullable = false)
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
