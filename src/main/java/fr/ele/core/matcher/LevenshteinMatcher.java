package fr.ele.core.matcher;

import java.util.HashMap;
import java.util.Map;

import fr.ele.core.matcher.LevenshteinDistance.LevenshteinResults;

public class LevenshteinMatcher implements StringMatcher {

    public static enum Algo {
        DISTANCE {
            @Override
            public int distance(LevenshteinResults result) {
                return result.getDistance();
            }
        },
        DELETED {
            @Override
            public int distance(LevenshteinResults result) {
                return result.getDeleted();
            }
        },
        INSERTED {
            @Override
            public int distance(LevenshteinResults result) {
                return result.getInserted();
            }
        },
        SUBSTITUTED {
            @Override
            public int distance(LevenshteinResults result) {
                return result.getSubstituted();
            }
        };

        public abstract int distance(LevenshteinResults result);
    }

    private final Map<Algo, Integer> algos;

    public LevenshteinMatcher(int i) {
        algos = new HashMap<LevenshteinMatcher.Algo, Integer>(1);
        algos.put(Algo.DISTANCE, i);
    }

    public LevenshteinMatcher(Map<Algo, Integer> algos) {
        this.algos = algos;
    }

    @Override
    public boolean match(String a, String b) {
        LevenshteinDistance lv = new LevenshteinDistance();
        LevenshteinResults results = lv.getLevenshteinDistance(a, b);
        for (Map.Entry<Algo, Integer> algo : algos.entrySet()) {
            if (algo.getKey().distance(results) <= algo.getValue()) {
                return true;
            }
        }
        return false;
    }

}
