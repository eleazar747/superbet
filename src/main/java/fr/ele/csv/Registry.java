package fr.ele.csv;

import java.util.ArrayList;
import java.util.List;

import fr.ele.core.csv.CsvBeanProperties;
import fr.ele.core.csv.CsvBeanPropertiesBuilder;
import fr.ele.core.csv.GraphResolver;
import fr.ele.model.DataMapping;
import fr.ele.model.DataMappingMeta;
import fr.ele.model.HasCodeEntityMeta;
import fr.ele.model.SuperBetEntityMeta;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.BookMakerMeta;
import fr.ele.model.ref.Sport;
import fr.herman.metatype.model.MetaProperty;
import fr.herman.metatype.model.method.Setter;
import fr.herman.metatype.utils.ChainedGetter;

public class Registry {
    public static final List<CsvBeanProperties<?>> REGISTRY = new ArrayList<>();
    static {
        // @formatter:off
        REGISTRY.add(CsvBeanPropertiesBuilder.create(BookMaker.class, BookMaker::new)
                .add("id", SuperBetEntityMeta.id)
                .add("code", HasCodeEntityMeta.code)
                .add("url", BookMakerMeta.url)
                .add("url sync", BookMakerMeta.urlSync)
                .add("sync service", BookMakerMeta.synchronizerService)
                .add("encoding", BookMakerMeta.encoding).build());

        REGISTRY.add(CsvBeanPropertiesBuilder.create(Sport.class, Sport::new)
                .add("id", SuperBetEntityMeta.id)
                .add("code", HasCodeEntityMeta.code).build());

        REGISTRY.add(CsvBeanPropertiesBuilder.create(BetType.class, BetType::new)
                .add("id", SuperBetEntityMeta.id)
                .add("code", HasCodeEntityMeta.code).build());

        REGISTRY.add(CsvBeanPropertiesBuilder.create(DataMapping.class, DataMapping::new)
                .add("id", SuperBetEntityMeta.id)
                .add("bookmaker", String.class, ChainedGetter.from(DataMappingMeta.bookMaker).to(HasCodeEntityMeta.code),
                        (context,bean,value)->setByCode(context.getGraphResolver(), DataMappingMeta.bookMaker, bean, value))
                .add("ref_entity_type", DataMappingMeta.refEntityType)
                .add("bookmaker_code", DataMappingMeta.bookMakerCode)
                .add("model_code", DataMappingMeta.modelCode)
                .build());

        REGISTRY.add(CsvBeanPropertiesBuilder.create(BetType.class, BetType::new)
                .add("id", SuperBetEntityMeta.id)
                .add("code", HasCodeEntityMeta.code).build());
        // @formatter:on
    }

    @SuppressWarnings("unchecked")
    private static final <BEAN, VALUE, P extends Setter<? super BEAN, VALUE> & MetaProperty<? super BEAN, VALUE>> void setByCode(
            GraphResolver resolver, P setter, BEAN bean, String code) {
        setter.setValue(bean, (VALUE) resolver.findByCode(setter.type(), code));
    }

    @SuppressWarnings("unchecked")
    public static <T> CsvBeanProperties<T> findCsvDefinition(Class<T> clazz) {
        for (CsvBeanProperties<?> registry : REGISTRY) {
            if (registry.getHandledClass().isAssignableFrom(clazz)) {
                return (CsvBeanProperties<T>) registry;
            }
        }
        throw new RuntimeException(clazz.getName() + "not handled");
    }
}
