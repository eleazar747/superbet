package fr.ele.feeds;

import java.util.Date;

public class HtmlBetDto {

	private String match;
	private String sport;
	private String betType;
	private double odd;
	private Date date;
	private String subType;
	private String bookmaker;
	private String bookmakerId;
	private String player1;
	private String player2;

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
		String[] players = match.split(" - ");
		this.setPlayer1(players[0]);
		this.setPlayer2(players[1]);
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getBookmaker() {
		return bookmaker;
	}

	public void setBookmaker(String bookmaker) {
		this.bookmaker = bookmaker;
	}

	public String getBookmakerId() {
		return bookmakerId;
	}

	public void setBookmakerId(String bookmakerId) {
		this.bookmakerId = bookmakerId;
	}

}