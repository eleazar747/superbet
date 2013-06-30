package fr.ele.model.ref.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.EntityImpl;
import fr.ele.model.ref.Sport;

@Entity
@Table(name = "SPORT")
@Proxy(proxyClass=Sport.class)
public class SportImpl extends EntityImpl implements Sport {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
