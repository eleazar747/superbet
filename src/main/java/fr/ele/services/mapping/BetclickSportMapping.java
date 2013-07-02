package fr.ele.services.mapping;

import fr.ele.feeds.betclick.dto.SportBcDto;
import fr.ele.model.SuperBetMapping;
import fr.ele.model.ref.impl.SportImpl;

public class BetclickSportMapping implements Mapper <SportBcDto,SportImpl>{

	@Override
	public SportImpl map(SportBcDto source, SportImpl target) {
		// TODO Auto-generated method stub
		if(source.getName().toString().toLowerCase().contains("football")){
			target.setCode(SuperBetMapping.SportType.FOOTBALL);
		}
		if(source.getName().toString().toLowerCase().contains("tennis")){
			target.setCode(SuperBetMapping.SportType.TENNIS);
		}
		
		
		return target;
	}

}
