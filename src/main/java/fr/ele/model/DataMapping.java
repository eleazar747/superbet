package fr.ele.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface DataMapping extends Entity {
    String getModelCode();

    void setModelCode(String code);

    String getBookMakerCode();

    void setBookMakerCode(String code);

    RefEntityType getRefEntityType();

    void setRefEntityType(RefEntityType refEntityType);
}
