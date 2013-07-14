package fr.ele.model.ref;

import javax.persistence.MappedSuperclass;

import fr.ele.model.Entity;

@MappedSuperclass
public interface RefKey extends Entity {

    BetType getBetType();

    Match getMatch();

    void setBetType(BetType betType);

    void setMatch(Match match);
}
