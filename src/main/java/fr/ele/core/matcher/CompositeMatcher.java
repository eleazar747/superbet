package fr.ele.core.matcher;

public class CompositeMatcher implements StringMatcher {

    private final StringMatcher[] matchers;

    private CompositeMatcher(StringMatcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean match(String a, String b) {
        for (StringMatcher matcher : matchers) {
            if (matcher.match(a, b)) {
                return true;
            }
        }
        return false;
    }

}
