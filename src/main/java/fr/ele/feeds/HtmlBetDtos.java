package fr.ele.feeds;

import java.util.List;
import java.util.TreeMap;

public class HtmlBetDtos {
	private List<HtmlBetDto> dtos;
	private TreeMap<String, HtmlBetDto> sortDtos, sortdtos;

	public List<HtmlBetDto> getDtos() {
		return dtos;
	}

	public void setDtos(List<HtmlBetDto> dtos) {
		this.dtos = dtos;
	}

	public String getSport() {

		return this.getDtos().get(0).getSport();
	}

	public TreeMap<String, HtmlBetDto> getDtosBetType() {
		for (HtmlBetDto htmlbetdto : dtos) {
			sortDtos.put(htmlbetdto.getBetType(), htmlbetdto);

		}
		sortdtos = (TreeMap<String, HtmlBetDto>) sortDtos.entrySet();
		// ddd
		return sortdtos;
	}
}
