package org.springframework.mapping;

import java.util.List;

import fr.ele.feeds.expekt.dto.Description;
import fr.ele.model.SuperBetMapping;
import fr.ele.model.ref.impl.BetTypeImpl;

public class ExpektBetMapping implements Mapper <Description,BetTypeImpl>{

	@Override
	public BetTypeImpl map(Description source, BetTypeImpl target) {
		// TODO Auto-generated method stub
		List list=source.getContent();
		if(list.size()>0){
			
			if(list.get(1).toString().contains(":")==false){
				target.setCode(SuperBetMapping.betType.RESULT_FINAL);
			}else
			{
				String[] value=list.get(1).toString().split(":");	
		
			if(value[1].toString().toLowerCase().replaceAll(" ", "").contains("drawnobet")){
				target.setCode(SuperBetMapping.betType.RESULT_FINAL);
			}
			if(value[1].toString().toLowerCase().replaceAll(" ", "").contains("over/under")){
				target.setCode(SuperBetMapping.betType.OVER_UNDER_TOTAL);
			}
			if(value[1].toString().toLowerCase().replaceAll(" ", "").contains("halftime")){
				target.setCode(SuperBetMapping.betType.RESULT_FIRST_HALF_TIME);
			}
			if(value[1].toString().toLowerCase().replaceAll(" ", "").contains("2ndhalfresult")){
				target.setCode(SuperBetMapping.betType.RESULT_SECOND_HALF_TIME);
			}
			}
			
		}
		
		return target;
	}
}
