package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.BookMaker;
import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.rest.AdminRestService;

@Transactional
@Service(AdminRestService.SERVER)
public class AdminRestServiceImpl implements AdminRestService {
    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Autowired
    private BookMakerSynchronizerService bookMakerSynchronizerService;

    @Override
    public SynchronizationResult synchronize(String bookmaker) {
        BookMaker b = bookMakerRepository.findByCode(bookmaker);
        if (b == null) {
            throw new RuntimeException(String.format("bookmaker %s not found",
                    bookmaker));
        }
        Long nbSynchronized = bookMakerSynchronizerService.synchronize(b);
        SynchronizationResult result = new SynchronizationResult();
        result.bookmaker = bookmaker;
        result.nb = nbSynchronized;
        return result;
    }

}
