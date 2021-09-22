package com.example.demo.schemaprovider;

import com.example.demo.schemaprovider.metamodel.OriginalEntityMetaModel;

import javax.persistence.Entity;
import java.util.List;

public interface MetaDataParsable<T> {

    List<OriginalEntityMetaModel> parse(Class<T> type);

    default void checkEntityClass(Class<T> clazz) {
        Entity declaredAnnotation = clazz.getDeclaredAnnotation(Entity.class);
        if (declaredAnnotation == null) {
            throw new IllegalArgumentException("Invalid type. Class to be parsed should be annotated with @Entity");
        }
    }
}
