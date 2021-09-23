package com.example.demo.schemaprovider.metamodel;

import com.example.demo.schemaprovider.annotation.SchemaDefinition;

public interface RootMetaModel extends MetaDataModel {

    Object getMetaDataValue(Object originalObject) throws IllegalAccessException;

    String getMetaDataDefinition();

}
