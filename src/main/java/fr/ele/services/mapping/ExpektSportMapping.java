package fr.ele.services.mapping;

import fr.ele.feeds.expekt.dto.Category;
import fr.ele.model.SuperBetMapping;
import fr.ele.model.ref.impl.SportImpl;

public class ExpektSportMapping implements Mapper <Category,SportImpl>{

	@Override
	public SportImpl map(Category source, SportImpl target) {
		// TODO Auto-generated method stub
		if(source.getId().toString().toLowerCase().startsWith("ten", 3));
		target.setCode(SuperBetMapping.SportType.TENNIS);
		
		
		if(source.getId().toString().toLowerCase().startsWith("soc", 3));
		target.setCode(SuperBetMapping.SportType.TENNIS);
		
		return target;
	}

}
