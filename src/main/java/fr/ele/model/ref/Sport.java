package fr.ele.model.ref;

import javax.persistence.Entity;
import javax.persistence.Table;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;
import fr.herman.metatype.annotation.MetaBean;

@MetaBean
@Entity
@Table(name = SuperBetTables.SportTable.TABLE)
public class Sport extends HasCodeEntity {

}
