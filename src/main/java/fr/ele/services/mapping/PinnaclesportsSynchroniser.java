package fr.ele.services.mapping;

import org.springframework.stereotype.Service;

import fr.ele.feeds.pinnaclesports.dto.Event;
import fr.ele.feeds.pinnaclesports.dto.Events;
import fr.ele.feeds.pinnaclesports.dto.PinnacleLineFeed;

@Service("PinnaclesportsSynchroniser")
public class PinnaclesportsSynchroniser extends
		AbstractSynchronizer<PinnacleLineFeed> {

	@Override
	protected long convert(SynchronizerContext context, PinnacleLineFeed dto) {
		// TODO Auto-generated method stub
		long nb = 0L;
		Events events = dto.getEvents();
		for (Event event : events.getEvent()) {

		}
		return nb;
	}

	@Override
	protected Class<PinnacleLineFeed> getDtoClass() {
		// TODO Auto-generated method stub
		return PinnacleLineFeed.class;
	}

	protected long convert(SynchronizerContext context, Event e) {
		long nb = 0L;

		return nb;
	}

}
