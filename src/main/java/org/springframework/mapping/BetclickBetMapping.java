package org.springframework.mapping;

import fr.ele.feeds.betclick.dto.BetBcDto;
import fr.ele.model.SuperBetMapping;
import fr.ele.model.ref.impl.BetTypeImpl;

public class BetclickBetMapping implements Mapper <BetBcDto,BetTypeImpl>{

	@Override
	public BetTypeImpl map(BetBcDto source, BetTypeImpl target) {
		// TODO Auto-generated method stub
		if(source.getName().toString().toLowerCase().replaceAll(" ", "").contains("match result")){
			target.setCode(SuperBetMapping.betType.RESULT_FINAL);
		}
		if(source.getName().toLowerCase().replaceAll(" ", "").contains("half-timeresult")){
			target.setCode(SuperBetMapping.betType.RESULT_FIRST_HALF_TIME);
		}
		if(source.getName().toLowerCase().replaceAll(" ", "").contains("totalgoallines")){
			target.setCode(SuperBetMapping.betType.OVER_UNDER_TOTAL);
		}
		
		return target;
	}

}
