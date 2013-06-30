package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.EntityImpl;
import fr.ele.model.SuperBetTables;
import fr.ele.model.ref.BetType;

@Entity
@Table(name = SuperBetTables.BetTypeTable.TABLE)
@Proxy(proxyClass=BetType.class)
public class BetTypeImpl extends EntityImpl implements BetType {

    @Column(name = SuperBetTables.BetTypeTable.CODE_COLUMN, nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
