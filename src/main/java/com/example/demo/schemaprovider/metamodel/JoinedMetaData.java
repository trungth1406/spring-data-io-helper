package com.example.demo.schemaprovider.metamodel;

import com.example.demo.schemaprovider.annotation.SchemaDefinition;

import java.lang.reflect.Field;

public class JoinedMetaData extends EntityMetadataModel {

    private final MetaDataModel root;

    public JoinedMetaData(MetaDataModel root, Field metaField, Class<?> assignableClass) {
        super(metaField, assignableClass);
        this.root = root;
    }


    @Override
    public String getPath() {
        return String.join(".", this.root.getPath(), this.metaField.getName());
    }


    @Override
    public String[] getMetaDataDefinition() {
        return this.metaField.getDeclaredAnnotation(SchemaDefinition.class).headerNames();
    }

    @Override
    public Object getMetaDataValue(Object rootObject) throws IllegalAccessException, NoSuchFieldException {
        Object rootValue = this.root.getMetaDataValue(rootObject);
        Field nestedField = rootValue.getClass().getDeclaredField(this.metaField.getName());
        Object thisValue = nestedField.get(rootValue);
        return this.metaField.get(thisValue);
    }
}
