package fr.ele.mapreduce;

public interface HolderFactory<R, V> {
    Holder<R, V> createHolder();
}
