package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.Sport;

@HandledClass(Sport.class)
public interface SportRepository extends SuperBetRepository<Sport>,
        HasCodeRepository<Sport> {

}
