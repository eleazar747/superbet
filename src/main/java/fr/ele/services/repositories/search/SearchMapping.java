package fr.ele.services.repositories.search;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.QDataMapping;
import fr.ele.model.ref.QBetType;
import fr.ele.model.ref.QBookMaker;
import fr.ele.model.ref.QSport;
import fr.ele.model.search.BetTypeSearch;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.model.search.DataMappingSearch;

public class SearchMapping {
    public static void map(QueryBuilder builder, QBookMaker bookmaker,
            BookmakerSearch search) {
        builder.and(bookmaker.code, search.getCode())
                .and(bookmaker.id, search.getId())
                .and(bookmaker.url, search.getUrl());
    }

    public static void map(QueryBuilder builder, QDataMapping datamapping,
            DataMappingSearch search) {
        builder.and(datamapping.bookMaker.code, search.getBookmakerCode())
                .and(datamapping.bookMakerCode, search.getBookmakerValue())
                .and(datamapping.modelCode, search.getModelValue())
                .and(datamapping.refEntityType, search.getRefEntityType());
    }

    public static void map(QueryBuilder builder, QBetType betType,
            BetTypeSearch search) {
        builder.and(betType.id, search.getId()).and(betType.code,
                search.getCode());
    }

    public static void map(QueryBuilder builder, QSport sport,
            BetTypeSearch search) {
        builder.and(sport.id, search.getId()).and(sport.code, search.getCode());
    }
}
