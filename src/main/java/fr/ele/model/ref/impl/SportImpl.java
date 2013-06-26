package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.ref.Sport;
import fr.ele.model.util.EntityImpl;

@Entity
@Table(name = "SPORT")
public class SportImpl extends EntityImpl implements Sport {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
