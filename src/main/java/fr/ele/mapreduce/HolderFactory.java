package fr.ele.mapreduce;

public interface HolderFactory<H extends Holder> {
    H createHolder();
}
