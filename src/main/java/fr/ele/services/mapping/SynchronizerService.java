package fr.ele.services.mapping;

import java.io.InputStream;

import fr.ele.model.BookMakerSynchronization;

public interface SynchronizerService<T> {
    BookMakerSynchronization synchronize(String bookmakerCode, T dto);

    T unmarshall(InputStream inputStream, String charset);
}
