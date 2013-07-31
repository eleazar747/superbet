package fr.ele.ui.model;

public interface MetaRegistry {
    MetaMapping getMetaMapping(Class<?> mappedClass);

    MetaMapping getMetaMapping(String id);
}
