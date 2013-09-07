package fr.ele.services.mapping;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.ref.BookMaker;

public interface BookMakerSynchronizerService {

    BookMakerSynchronization synchronize(BookMaker bookMaker);
}
