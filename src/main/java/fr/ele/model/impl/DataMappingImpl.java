package fr.ele.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.DataMapping;
import fr.ele.model.EntityImpl;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.DataMapping.TABLE)
public class DataMappingImpl extends EntityImpl implements DataMapping {

    @Column(name = SuperBetTables.DataMapping.MODEL_CODE, nullable = false)
    private String modelCode;

    @Column(name = SuperBetTables.DataMapping.BOOKMAKER_CODE, nullable = false)
    private String bookMakerCode;

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

}
