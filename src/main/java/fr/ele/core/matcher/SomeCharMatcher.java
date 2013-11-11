package fr.ele.core.matcher;

public class SomeCharMatcher implements StringMatcher {

    private final int treshold;

    public SomeCharMatcher() {
        this(1);
    }

    public SomeCharMatcher(int treshold) {
        this.treshold = treshold;
    }

    private double similarity(String a, String b) {
        double count = 0;
        char[] words = a.toUpperCase().toCharArray();
        String other = b.toUpperCase();
        int previousIndex = -1;
        for (char word : words) {
            int index = other.indexOf(word, previousIndex + 1);
            if (index != -1) {
                count++;
                previousIndex = index;
            }
        }
        return count;
    }

    @Override
    public boolean match(String a, String b) {
        double count = similarity(a, b);
        return Math.abs(a.length() - count) <= treshold
                && Math.abs(b.length() - count) <= treshold;
    }

}
