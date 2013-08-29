package fr.ele.mapreduce;

public interface Closure<T> {
    void execute(T input);
}
