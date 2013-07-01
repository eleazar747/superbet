package fr.ele.core.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class GenericRegistry<T, K> {
    private final Map<K, T> map = new HashMap<K, T>();

    public T register(final T entity) {
        if (RegistrableEntity.class.isAssignableFrom(entity.getClass())) {
            RegistrableEntity registrable = (RegistrableEntity) entity;
            return map.put((K) registrable.getHandledKey(), entity);
        }
        throw new IllegalArgumentException("cannot register object :" + entity);
    }

    public T register(final K key, T entity) {
        return map.put(key, entity);
    }

    public T lookup(final K key) {
        final T result = map.get(key);

        if (result != null) {
            return result;
        }

        for (final Entry<K, T> entry : map.entrySet()) {
            final K mapKey = entry.getKey();

            if (areKeysEquals(mapKey, key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    protected abstract boolean areKeysEquals(K key1, K key2);

    public T safeLookup(final K klass) {
        final T result = lookup(klass);

        if (result == null) {
            return getDefaultObject(klass);
        }
        return result;
    }

    protected abstract T getDefaultObject(K key);
}
