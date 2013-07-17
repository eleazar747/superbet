package fr.ele.services.repositories;

import com.mysema.query.types.Predicate;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.RefEntityType;

@HandledClass(DataMapping.class)
public interface DataMappingRepository extends SuperBetRepository<DataMapping> {

    public abstract static class Queries {
        public static final Predicate findModelByBookMaker(RefEntityType type,
                String bookMakerCode) {
            QDataMapping dataMapping = QDataMapping.dataMapping;
            return dataMapping.refEntityType.eq(type).and(
                    dataMapping.bookMakerCode.eq(bookMakerCode));
        }
    }
}
