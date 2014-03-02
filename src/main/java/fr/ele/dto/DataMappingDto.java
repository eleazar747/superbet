package fr.ele.dto;

import fr.ele.model.RefEntityType;

public class DataMappingDto extends HasIdDto {
    private String modelCode, bookMakerCode;

    private RefEntityType refEntityType;

    private BookmakerDto bookMaker;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getBookMakerCode() {
        return bookMakerCode;
    }

    public void setBookMakerCode(String bookMakerCode) {
        this.bookMakerCode = bookMakerCode;
    }

    public RefEntityType getRefEntityType() {
        return refEntityType;
    }

    public void setRefEntityType(RefEntityType refEntityType) {
        this.refEntityType = refEntityType;
    }

    public BookmakerDto getBookMaker() {
        return bookMaker;
    }

    public void setBookMaker(BookmakerDto bookMaker) {
        this.bookMaker = bookMaker;
    }

}
