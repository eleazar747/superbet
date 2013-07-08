package fr.ele.model;


public interface DataMapping extends Entity {
    String getModelCode();

    void setModelCode(String code);

    String getBookMakerCode();

    void setBookMakerCode(String code);
}
