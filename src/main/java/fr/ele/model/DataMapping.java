package fr.ele.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = SuperBetTables.DataMapping.TABLE)
public class DataMapping extends SuperBetEntity {

    @Column(name = SuperBetTables.DataMapping.MODEL_CODE, nullable = false)
    private String modelCode;

    @Column(name = SuperBetTables.DataMapping.BOOKMAKER_CODE, nullable = false)
    private String bookMakerCode;

    @Column(name = SuperBetTables.DataMapping.REF_ENTITY_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    private RefEntityType refEntityType;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getBookMakerCode() {
        return bookMakerCode;
    }

    public void setBookMakerCode(String bookMakerCode) {
        this.bookMakerCode = bookMakerCode;
    }

    public RefEntityType getRefEntityType() {
        return refEntityType;
    }

    public void setRefEntityType(RefEntityType refEntityType) {
        this.refEntityType = refEntityType;
    }

}
