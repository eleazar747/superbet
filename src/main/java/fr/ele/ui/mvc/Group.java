package fr.ele.ui.mvc;

import java.util.LinkedList;
import java.util.List;

public class Group {
    private final String name;

    private final List<Activity> activities = new LinkedList<Activity>();

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void add(Activity activity) {
        activities.add(activity);
    }
}
