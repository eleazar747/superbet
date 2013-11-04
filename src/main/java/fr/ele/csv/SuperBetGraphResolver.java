package fr.ele.csv;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ele.core.csv.GraphResolver;
import fr.ele.model.SuperBetEntity;
import fr.ele.services.repositories.HasCodeRepository;
import fr.ele.services.repositories.RepositoryRegistry;
import fr.ele.services.repositories.SuperBetRepository;

public class SuperBetGraphResolver implements GraphResolver {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(SuperBetGraphResolver.class);

    private final RepositoryRegistry registry;

    private final Map<Key, Object> cache = new HashMap<Key, Object>();

    private static class Key {
        private final Class<?> clazz;

        private final Object key;

        public Key(Class<?> clazz, Object key) {
            super();
            this.clazz = clazz;
            this.key = key;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (clazz == null ? 0 : clazz.hashCode());
            result = prime * result + (key == null ? 0 : key.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Key other = (Key) obj;
            if (clazz == null) {
                if (other.clazz != null) {
                    return false;
                }
            } else if (!clazz.equals(other.clazz)) {
                return false;
            }
            if (key == null) {
                if (other.key != null) {
                    return false;
                }
            } else if (!key.equals(other.key)) {
                return false;
            }
            return true;
        }

    }

    public SuperBetGraphResolver(RepositoryRegistry registry) {
        this.registry = registry;
    }

    @Override
    public <T extends SuperBetEntity> SuperBetRepository<T> getRepository(
            Class<T> entityClass) {
        return registry.getRepository(entityClass);
    }

    @Override
    public <T> T findByCode(Class<T> clazz, String code) {
        SuperBetRepository repository = registry
                .getRepository((Class<SuperBetEntity>) clazz);
        if (repository instanceof HasCodeRepository) {
            Key key = new Key(clazz, code);
            if (cache.containsKey(key)) {
                return (T) cache.get(key);
            }
            LOGGER.debug("Load key({},{})", clazz.getName(), code);
            T byCode = (T) ((HasCodeRepository) repository).findByCode(code);
            cache.put(key, byCode);
            return byCode;
        }
        throw new RuntimeException("repo hasn't findByCode method");
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id) {
        SuperBetRepository repository = registry
                .getRepository((Class<SuperBetEntity>) clazz);
        return (T) repository.findOne(id);
    }

}
