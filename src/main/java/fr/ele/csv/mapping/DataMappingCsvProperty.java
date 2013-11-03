package fr.ele.csv.mapping;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvProperty;
import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.repositories.BookMakerRepository;

public enum DataMappingCsvProperty implements CsvProperty<DataMapping> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<DataMapping> context,
                DataMapping object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue(CsvContext context, DataMapping object,
                String value) {
            ((SuperBetEntity) object).setId((Long) context.unmarshall(
                    Long.class, value));
        }
    },
    BOOKMAKER("bookmaker") {

        @Override
        public String getStringValue(CsvContext<DataMapping> context,
                DataMapping object) {
            return context.marshall(object.getBookMaker().getCode());
        }

        @Override
        public void setValue(CsvContext context, DataMapping object,
                String value) {
            BookMakerRepository repository = (BookMakerRepository) context
                    .getRepository(BookMaker.class);
            object.setBookMaker(repository.findByCode((String) context
                    .unmarshall(String.class, value)));
        }
    },
    REF_ENTITY_TYPE("ref_entity_type") {

        @Override
        public String getStringValue(CsvContext<DataMapping> context,
                DataMapping object) {
            return context.marshall(object.getRefEntityType().name());
        }

        @Override
        public void setValue(CsvContext context, DataMapping object,
                String value) {
            object.setRefEntityType(RefEntityType.valueOf((String) context
                    .unmarshall(String.class, value)));
        }
    },
    BOOKMAKER_CODE("bookmaker_code") {

        @Override
        public String getStringValue(CsvContext<DataMapping> context,
                DataMapping object) {
            return context.marshall(object.getBookMakerCode());
        }

        @Override
        public void setValue(CsvContext context, DataMapping object,
                String value) {
            object.setBookMakerCode((String) context.unmarshall(String.class,
                    value));
        }
    },
    MODEL_CODE("model_code") {

        @Override
        public String getStringValue(CsvContext<DataMapping> context,
                DataMapping object) {
            return context.marshall(object.getModelCode());
        }

        @Override
        public void setValue(CsvContext context, DataMapping object,
                String value) {
            object.setModelCode((String) context
                    .unmarshall(String.class, value));
        }
    };

    private final String header;

    private DataMappingCsvProperty(String header) {
        this.header = header;
    }

    @Override
    public String getHeader() {
        return header;
    }
}
