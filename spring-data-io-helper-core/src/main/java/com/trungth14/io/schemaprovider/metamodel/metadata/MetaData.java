package com.trungth14.io.schemaprovider.metamodel.metadata;

import java.util.function.Function;

public interface MetaData {

    String[] getMetaDataDefinition();

    Object getMetaDataValueFrom(Object originalObject) throws Exception;

    Object getMetaDataValueFrom(Object originalObject, Function<Object, Object> appliedFunc) throws Exception;

}
