package fr.ele.services.repositories.search;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.QBet;
import fr.ele.model.QDataMapping;
import fr.ele.model.ref.QBetType;
import fr.ele.model.ref.QBookMaker;
import fr.ele.model.ref.QMatch;
import fr.ele.model.ref.QSport;
import fr.ele.model.search.BetSearch;
import fr.ele.model.search.BetTypeSearch;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.model.search.DataMappingSearch;
import fr.ele.model.search.MatchSearch;

public class SearchMapping {
    public static void map(QueryBuilder builder, QBookMaker bookmaker,
            BookmakerSearch search) {
        if (search != null) {
            builder.and(bookmaker.code, search.getCode())
                    .and(bookmaker.id, search.getId())
                    .and(bookmaker.url, search.getUrl());
        }
    }

    public static void map(QueryBuilder builder, QDataMapping datamapping,
            DataMappingSearch search) {
        if (search != null) {
            builder.and(datamapping.bookMakerCode, search.getBookmakerValue())
                    .and(datamapping.modelCode, search.getModelValue())
                    .and(datamapping.refEntityType, search.getRefEntityType());
            map(builder, datamapping.bookMaker, search.getBookmaker());
        }
    }

    public static void map(QueryBuilder builder, QBetType betType,
            BetTypeSearch search) {
        if (search != null) {
            builder.and(betType.id, search.getId()).and(betType.code,
                    search.getCode());
        }
    }

    public static void map(QueryBuilder builder, QSport sport,
            BetTypeSearch search) {
        if (search != null) {
            builder.and(sport.id, search.getId()).and(sport.code,
                    search.getCode());
        }
    }

    public static void map(QueryBuilder builder, QMatch match,
            MatchSearch search) {
        if (search != null) {

            builder.and(match.code, search.getCode())
                    .and(match.sport.code, search.getSport())
                    .and(match.date, search.getDate());
        }
    }

    public static void map(QueryBuilder builder, QBet bet, BetSearch search) {
        if (search != null) {
            builder.and(bet.date, search.getSyncDate());

            map(builder, bet.bookMaker, search.getBookmaker());
            map(builder, bet.refKey.betType, search.getBetType());
            map(builder, bet.refKey.match, search.getMatch());
        }
    }
}
