package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.EntityImpl;
import fr.ele.model.ref.BetType;

@Entity
@Table(name = "BET_TYPE")
public class BetTypeImpl extends EntityImpl implements BetType {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
