package com.example.demo.schemaprovider.metamodel;

import java.lang.reflect.Field;
import java.util.Locale;

public abstract class EntityMetadataModel implements MetaDataModel {

    protected Field metaField;
    protected Class<?> assignableClass;


    public EntityMetadataModel(Field metaField, Class<?> assignableClass) {
        this.metaField = metaField;
        this.assignableClass = assignableClass;

    }

    public String getPath() {
        String entityName = this.assignableClass.getSimpleName().toLowerCase(Locale.getDefault());
        return String.join(".", entityName, this.metaField.getName());
    }

}
