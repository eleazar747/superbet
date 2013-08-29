package fr.ele.mapreduce;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class KeyMap<K extends Key, R, V> {

    private final Map<K, Holder<R, V>> map;

    private final HolderFactory<R, V> factory;

    public KeyMap(HolderFactory<R, V> factory, Map<K, Holder<R, V>> map) {
        this.map = map;
        this.factory = factory;
    }

    public KeyMap(HolderFactory<R, V> factory) {
        this(factory, new HashMap());
    }

    public void add(K key, V value) {
        Holder<R, V> holder = map.get(key);
        if (holder == null) {
            holder = factory.createHolder();
            map.put(key, holder);
        }
        holder.add(value);
    }

    public Iterator<Entry<K, Holder<R, V>>> iterate() {
        return map.entrySet().iterator();
    }

    public Iterator<Holder<R, V>> iterateHolder() {
        return map.values().iterator();
    }

    public Collection<Holder<R, V>> getHolders() {
        return map.values();
    }
}
