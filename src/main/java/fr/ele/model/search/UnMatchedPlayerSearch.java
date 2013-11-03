package fr.ele.model.search;

import fr.ele.core.search.Search;

public class UnMatchedPlayerSearch implements Search {

    private BookmakerSearch bookmaker;

    private SportSearch sport;

    public BookmakerSearch getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(BookmakerSearch bookmaker) {
        this.bookmaker = bookmaker;
    }

    public SportSearch getSport() {
        return sport;
    }

    public void setSport(SportSearch sport) {
        this.sport = sport;
    }

}
