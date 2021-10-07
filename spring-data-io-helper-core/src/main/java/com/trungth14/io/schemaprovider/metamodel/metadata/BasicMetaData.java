package com.trungth14.io.schemaprovider.metamodel.metadata;

import java.lang.reflect.Field;

public class BasicMetaData extends  AbstractMetaDataModel {

    public BasicMetaData(Field metaField, Class<?> assignableClass) {
        super(metaField, assignableClass);
    }
}
