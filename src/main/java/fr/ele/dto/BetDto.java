package fr.ele.dto;

import java.util.Date;

import com.codiform.moo.annotation.Property;

public class BetDto extends HasIdDto {

    @Property(translate = true)
    private BookmakerDto bookmaker;

    @Property(translation = "refKey.betType", translate = true)
    private BetTypeDto betType;

    @Property(translation = "refKey.match", translate = true)
    private MatchDto match;

    private Date date;

    private double odd;

    private String code;

    public BookmakerDto getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(BookmakerDto bookmaker) {
        this.bookmaker = bookmaker;
    }

    public BetTypeDto getBetType() {
        return betType;
    }

    public void setBetType(BetTypeDto betType) {
        this.betType = betType;
    }

    public MatchDto getMatch() {
        return match;
    }

    public void setMatch(MatchDto match) {
        this.match = match;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
