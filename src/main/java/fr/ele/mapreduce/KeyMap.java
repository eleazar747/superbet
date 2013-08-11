package fr.ele.mapreduce;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;

public class KeyMap<K extends Key, R, V, H extends Holder<R, V>> {

    public static final class Pair<G, D> {
        private final G left;

        private final D right;

        public Pair(G left, D right) {
            this.left = left;
            this.right = right;
        }

        public G getLeft() {
            return left;
        }

        public D getRight() {
            return right;
        }

    }

    private final Map<K, H> map;

    private final HolderFactory<H> factory;

    public KeyMap(HolderFactory<H> factory, Map<K, H> map) {
        this.map = map;
        this.factory = factory;
    }

    public KeyMap(HolderFactory<H> factory) {
        this(factory, new HashMap<K, H>());
    }

    public void add(K key, V value) {
        H holder = map.get(key);
        if (holder == null) {
            holder = factory.createHolder();
            map.put(key, holder);
        }
        holder.add(value);
    }

    Iterator<Entry<K, H>> iterate() {
        return map.entrySet().iterator();
    }

    Iterator<Pair<K, R>> iterateResults() {
        return Iterators.transform(iterate(),
                new Function<Entry<K, H>, Pair<K, R>>() {

                    @Override
                    @Nullable
                    public Pair<K, R> apply(@Nullable Entry<K, H> entry) {
                        return new Pair(entry.getKey(), entry.getValue()
                                .getResult());
                    };
                });
    }
}
