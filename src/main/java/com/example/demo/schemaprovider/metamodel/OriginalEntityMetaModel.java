package com.example.demo.schemaprovider.metamodel;


import com.example.demo.schemaprovider.annotation.SchemaDefinition;

import java.lang.reflect.Field;

public class OriginalEntityMetaModel extends EntityMetadataModel {


    public OriginalEntityMetaModel(Class<?> assignableClass, Field columnField) {
        super(columnField, assignableClass);
    }

    public Object getMetaDataValue(Object originalObject) throws IllegalAccessException {
        if (!originalObject.getClass().isAssignableFrom(this.assignableClass)) {
            throw new IllegalStateException("Original object is not assignable from " + assignableClass.getName());
        }
        this.metaField.setAccessible(true);
        return this.metaField.get(originalObject);
    }

    public String getMetaDataDefinition() {
        SchemaDefinition columnName = this.metaField.getDeclaredAnnotation(SchemaDefinition.class);
        if (columnName == null) {
            throw new IllegalStateException("Field should be annotated with @SchemaDefinition");
        }
        return columnName.columnName();
    }



}
