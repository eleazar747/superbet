package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.ref.BetType;
import fr.ele.model.util.EntityImpl;

@Entity
@Table(name = "BET_TYPE")
public class BetTypeImpl extends EntityImpl implements BetType {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
