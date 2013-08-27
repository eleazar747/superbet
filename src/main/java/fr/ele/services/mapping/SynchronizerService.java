package fr.ele.services.mapping;

import java.io.InputStream;

public interface SynchronizerService<T> {
    long synchronize(String bookmakerCode, T dto);

    T unmarshall(InputStream inputStream);
}
