package fr.ele.ui;

public class ModelAndView {
    private final Object model;

    private final String view;

    public ModelAndView(Object model, String view) {
        this.model = model;
        this.view = view;
    }

    public Object getModel() {
        return model;
    }

    public String getView() {
        return view;
    }

}
