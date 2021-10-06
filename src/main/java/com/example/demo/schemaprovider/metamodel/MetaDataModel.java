package com.example.demo.schemaprovider.metamodel;

public interface MetaDataModel {

    String[] getMetaDataDefinition();

    Object getMetaDataValue(Object paramObject) throws IllegalAccessException, NoSuchFieldException;

    String getPath();
}
