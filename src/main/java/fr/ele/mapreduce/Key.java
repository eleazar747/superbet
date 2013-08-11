package fr.ele.mapreduce;

public abstract class Key {

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

}
