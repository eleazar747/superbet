package fr.ele.services.dao.impl;

import fr.ele.model.Bet;
import fr.ele.services.dao.BetDao;

public class BetDaoImpl extends GenericDaoImpl<Bet> implements BetDao {

	public BetDaoImpl() {
		super(Bet.class);
	}

}
