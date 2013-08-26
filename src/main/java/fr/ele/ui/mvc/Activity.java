package fr.ele.ui.mvc;

public class Activity {

    private final String name;

    private final String uri;

    public Activity(String name, String uri) {
        super();
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

}
