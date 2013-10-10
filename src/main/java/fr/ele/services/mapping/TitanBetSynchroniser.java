package fr.ele.services.mapping;

import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Odds;

@Service("TitanBetSynchroniser")
public class TitanBetSynchroniser extends AbstractSynchronizer<Odds> {

	@Override
	protected long convert(SynchronizerContext context, Odds dto) {
		// TODO Auto-generated method stub
		long nb = 0L;

		return nb;
	}

	@Override
	protected Class<Odds> getDtoClass() {
		// TODO Auto-generated method stub
		return Odds.class;
	}

}
