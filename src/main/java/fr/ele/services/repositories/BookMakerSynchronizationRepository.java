package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.BookMakerSynchronization;

@HandledClass(BookMakerSynchronization.class)
public interface BookMakerSynchronizationRepository extends
        SuperBetRepository<BookMakerSynchronization> {
}
