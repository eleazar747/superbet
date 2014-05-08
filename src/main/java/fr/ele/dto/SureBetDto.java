package fr.ele.dto;

import java.util.Map;

public class SureBetDto {
    private String sport, betType, match;

    private String odds, date, profit;

    private Map<String, Double> alternatives;

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

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public Map<String, Double> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Map<String, Double> alternatives) {
        this.alternatives = alternatives;
    }

}
