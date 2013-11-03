package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.UnMatchedPlayer;

@HandledClass(UnMatchedPlayer.class)
public interface UnMatchedPlayerRepository extends
        SuperBetRepository<UnMatchedPlayer> {

}
