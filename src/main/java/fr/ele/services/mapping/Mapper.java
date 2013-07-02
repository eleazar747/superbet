package fr.ele.services.mapping;

public interface Mapper<S, T> {

    T map(S source, T target);   
        
}
