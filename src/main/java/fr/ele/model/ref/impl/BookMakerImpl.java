package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.EntityImpl;
import fr.ele.model.SuperBetTables;
import fr.ele.model.ref.BookMaker;

@Entity
@Table(name = SuperBetTables.BookMakerTable.TABLE)
@Proxy(proxyClass = BookMaker.class)
public class BookMakerImpl extends EntityImpl implements BookMaker {

    @Column(name = SuperBetTables.BookMakerTable.CODE_COLUMN, nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
