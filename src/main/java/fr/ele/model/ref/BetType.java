package fr.ele.model.ref;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;
import fr.herman.metatype.annotation.MetaBean;

@Entity
@MetaBean
@Table(name = SuperBetTables.BetTypeTable.TABLE)
@Proxy(proxyClass = BetType.class)
public class BetType extends HasCodeEntity {

}
