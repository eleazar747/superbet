package fr.ele.services.repositories.custom;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.ref.BookMaker;

public interface BookMakerSynchronizationRespositoryCustom {
    BookMakerSynchronization lastSync(BookMaker bookMaker);
}
