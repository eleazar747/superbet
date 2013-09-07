package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.BookMakerSynchronizationRepository;
import fr.ele.services.rest.AdminRestService;

@Transactional
@Service(AdminRestService.SERVER)
public class AdminRestServiceImpl implements AdminRestService {
    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Autowired
    private BookMakerSynchronizerService bookMakerSynchronizerService;

    @Autowired
    private BookMakerSynchronizationRepository bookMakerSynchronizationRepository;

    @Override
    public BookMakerSynchronization synchronize(String bookmaker) {
        BookMaker b = bookMakerRepository.findByCode(bookmaker);
        if (b == null) {
            throw new RuntimeException(String.format("bookmaker %s not found",
                    bookmaker));
        }
        return bookMakerSynchronizerService.synchronize(b);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<BookMakerSynchronization> listSyncs() {
        return bookMakerSynchronizationRepository.findAll();
    }

}
