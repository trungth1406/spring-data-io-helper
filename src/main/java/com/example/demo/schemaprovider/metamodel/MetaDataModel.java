package com.example.demo.schemaprovider.metamodel;

public interface MetaDataModel {

    public Object getMetaDataValue(Object originalObject) throws IllegalAccessException;

    public String getMetaDataDefinition();
}
