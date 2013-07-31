package fr.ele.ui.model.superbet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import fr.ele.ui.model.MetaMapping;
import fr.ele.ui.model.MetaRegistry;

@Component
public class SuperBetMetaRegistryFactory implements MetaRegistry {

    private static final Map<String, MetaMapping> CACHE;
    static {
        CACHE = new HashMap<String, MetaMapping>(
                SuperBetUiMetaMapping.values().length);
        for (SuperBetUiMetaMapping mapping : SuperBetUiMetaMapping.values()) {
            CACHE.put(mapping.getIdentifier(), mapping);
        }
    }

    @Override
    public MetaMapping getMetaMapping(Class<?> mappedClass) {
        for (MetaMapping mapping : SuperBetUiMetaMapping.values()) {
            if (mapping.handledClass().isAssignableFrom(mappedClass)) {
                return mapping;
            }
        }
        return null;
    }

    @Override
    public MetaMapping getMetaMapping(String id) {
        return CACHE.get(id);
    }

}
