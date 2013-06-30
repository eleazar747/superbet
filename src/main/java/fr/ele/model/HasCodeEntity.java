package fr.ele.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface HasCodeEntity extends Entity {
    String getCode();
    void setCode(String code);
}
