package fr.ele.model.ref;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.BookMakerTable.TABLE)
@Proxy(proxyClass = BookMaker.class)
public class BookMaker extends HasCodeEntity {

}
