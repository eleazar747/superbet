package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.BetType;

@HandledClass(BetType.class)
public interface BetTypeRepository extends SuperBetRepository<BetType>,
        HasCodeRepository<BetType> {

}
