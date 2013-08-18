package fr.ele.core;

public class TimeTracker {
    private final long startTime;

    public TimeTracker() {
        startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return System.currentTimeMillis() - startTime;
    }

    public long getDurationSecond() {
        return getDuration() / 1000;
    }
}
