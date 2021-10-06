package com.example.demo.schemaprovider.metadata;

import com.example.demo.schemaprovider.annotation.SchemaDefinition;
import org.hibernate.Hibernate;

import java.lang.reflect.Field;

public class BasicMetaData implements ExportMetaData {

    private final Class<?> assignableClass;
    private final Field associatedField;

    public BasicMetaData(Class<?> assignableClass, Field associatedField) {
        this.assignableClass = assignableClass;
        this.associatedField = associatedField;
    }

    public String[] getMetaDataDefinition() {
        SchemaDefinition columnName = this.associatedField.getDeclaredAnnotation(SchemaDefinition.class);
        if (columnName == null) {
            throw new IllegalStateException("Field should be annotated with @SchemaDefinition");
        }
        return columnName.headerNames();
    }

    public Object getMetaDataValueFrom(Object originalObject) throws IllegalAccessException {
        if (originalObject == null) return null;
        Object unproxied = Hibernate.unproxy(originalObject);
        if (!unproxied.getClass().isAssignableFrom(this.assignableClass)) {
            throw new IllegalStateException("Original object is not assignable from " + assignableClass.getName());
        }
        this.associatedField.setAccessible(true);
        return this.associatedField.get(unproxied);
    }

}
