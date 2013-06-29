package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import fr.ele.model.Bet;
import fr.ele.services.dao.BetDao;

@Repository
public class BetDaoImpl extends GenericDaoImpl<Bet> implements BetDao {

	public BetDaoImpl() {
		super(Bet.class);
	}

}
