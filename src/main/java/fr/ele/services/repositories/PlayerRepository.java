package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.Player;

@HandledClass(Player.class)
public interface PlayerRepository extends SuperBetRepository<Player> {

}
