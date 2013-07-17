package fr.ele.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class HasCodeEntity extends SuperBetEntity {
    @Column(name = SuperBetTables.CODE_COLUMN, nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
