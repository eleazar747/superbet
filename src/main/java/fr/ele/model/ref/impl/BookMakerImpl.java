package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.ref.BookMarker;
import fr.ele.model.util.EntityImpl;

@Entity
@Table(name = "BOOKMAKER")
public class BookMakerImpl extends EntityImpl implements BookMarker {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
