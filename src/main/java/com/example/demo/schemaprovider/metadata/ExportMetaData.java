package com.example.demo.schemaprovider.metadata;

import java.lang.reflect.InvocationTargetException;

public interface ExportMetaData {

    String[] getMetaDataDefinition();

    Object getMetaDataValueFrom(Object originalObject) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException;
}
