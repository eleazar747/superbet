package fr.ele.mapreduce;

public interface Holder<R, V> {
    R getResult();

    void add(V value);
}
