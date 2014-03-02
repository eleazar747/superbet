package fr.ele.dto;

import java.util.Date;

public class MatchDto extends HasIdAndCodeDto {
    private Date date;

    private SportDto sport;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SportDto getSport() {
        return sport;
    }

    public void setSport(SportDto sport) {
        this.sport = sport;
    }
}
