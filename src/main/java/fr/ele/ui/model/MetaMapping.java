package fr.ele.ui.model;

import java.util.List;

public interface MetaMapping {
    List<MetaProperty> getMetaProperties();

    String getIdentifier();

    Class<?> handledClass();
}
