package fr.ele.services.mapping;

import fr.ele.model.ref.BookMaker;

public interface BookMakerSynchronizerService {

    Long synchronize(BookMaker bookMaker);
}
