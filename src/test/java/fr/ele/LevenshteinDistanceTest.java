package fr.ele;

import org.junit.Assert;
import org.junit.Test;

import fr.ele.core.matcher.LevenshteinDistance;

public class LevenshteinDistanceTest extends Assert {

    @Test
    public void testLd() {
        LevenshteinDistance ld = new LevenshteinDistance();
        LevenshteinDistance.LevenshteinResults r;
        String s1 = "bleep";
        String s2 = "bleep";
        r = ld.getLevenshteinDistance(s1, s2);
        assertEquals(0, r.getDistance());
        assertEquals(0, r.getDeleted());
        assertEquals(0, r.getDistance());
        assertEquals(0, r.getInserted());
        assertEquals(0, r.getSubstituted());
        s2 = "bleop";
        r = ld.getLevenshteinDistance(s1, s2);
        assertEquals(1, r.getDistance());

        assertEquals(0, r.getDeleted());
        assertEquals(1, r.getDistance());
        assertEquals(0, r.getInserted());
        assertEquals(1, r.getSubstituted());

        s2 = "blep";
        r = ld.getLevenshteinDistance(s1, s2);
        assertEquals(1, r.getDistance());
        assertEquals(1, r.getDeleted());
        assertEquals(1, r.getDistance());
        assertEquals(0, r.getInserted());
        assertEquals(0, r.getSubstituted());

    }

}
