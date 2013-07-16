package fr.ele.model.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.SuperBetEntity;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.BetTypeTable.TABLE)
@Proxy(proxyClass = BetType.class)
public class BetType extends SuperBetEntity {

    @Column(name = SuperBetTables.BetTypeTable.CODE_COLUMN, nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
