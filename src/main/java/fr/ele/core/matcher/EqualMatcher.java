package fr.ele.core.matcher;

public class EqualMatcher implements StringMatcher {

    @Override
    public boolean match(String a, String b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

}
