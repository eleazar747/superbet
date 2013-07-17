package fr.ele.services.repositories;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.BookMaker;

@HandledClass(BookMaker.class)
public interface BookMakerRepository extends SuperBetRepository<BookMaker>,
        HasCodeRepository<BookMaker> {
}
