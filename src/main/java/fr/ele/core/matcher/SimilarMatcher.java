package fr.ele.core.matcher;

public class SimilarMatcher implements StringMatcher {

    private final double treshold;

    public SimilarMatcher() {
        this(0.8);
    }

    public SimilarMatcher(double treshold) {
        this.treshold = treshold;
    }

    public static double similarity(String a, String b) {
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
        return (count / words.length + count / other.length()) / 2;
    }

    @Override
    public boolean match(String a, String b) {
        return similarity(a, b) >= treshold;
    }
}
