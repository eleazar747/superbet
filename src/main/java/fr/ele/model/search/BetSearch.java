package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.date.DateValueCriteria;

public class BetSearch implements Search {
    private MatchSearch match;

    private BookmakerSearch bookmaker;

    private BetTypeSearch betType;

    private DateValueCriteria syncDate;

    public MatchSearch getMatch() {
        return match;
    }

    public void setMatch(MatchSearch match) {
        this.match = match;
    }

    public BookmakerSearch getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(BookmakerSearch bookmaker) {
        this.bookmaker = bookmaker;
    }

    public BetTypeSearch getBetType() {
        return betType;
    }

    public void setBetType(BetTypeSearch betType) {
        this.betType = betType;
    }

    public DateValueCriteria getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(DateValueCriteria syncDate) {
        this.syncDate = syncDate;
    }

}
