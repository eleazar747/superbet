package fr.ele.core.matcher;


public class IgnoreCaseMatcher implements StringMatcher {

    @Override
    public boolean match(String a, String b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        String A = a.toUpperCase();
        String B = b.toUpperCase();
        return A.equals(B);
    }

}
