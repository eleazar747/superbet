package fr.ele.mapreduce.superbet;

import fr.ele.mapreduce.Holder;
import fr.ele.mapreduce.HolderFactory;

public class MaxBetHolderFactory implements HolderFactory {

    @Override
    public Holder createHolder() {
        return new MaxBetHolder();
    }

}
