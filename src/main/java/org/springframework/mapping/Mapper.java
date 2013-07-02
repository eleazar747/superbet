package org.springframework.mapping;

public interface Mapper<S, T> {

    T map(S source, T target);   
        
}
