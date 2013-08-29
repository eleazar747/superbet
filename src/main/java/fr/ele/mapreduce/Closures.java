package fr.ele.mapreduce;

import java.util.Iterator;

public final class Closures {
    private Closures() {

    }

    public static final <T> void forAll(Iterator<T> it, Closure<T> closure) {
        if (it != null) {
            while (it.hasNext()) {
                closure.execute(it.next());
            }
        }
    }

    public static final <T> void forAll(Iterable<T> iterable, Closure<T> closure) {
        if (iterable != null) {
            forAll(iterable.iterator(), closure);
        }
    }
}
