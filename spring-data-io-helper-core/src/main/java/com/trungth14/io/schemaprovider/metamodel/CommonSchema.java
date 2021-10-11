package com.trungth14.io.schemaprovider.metamodel;

public interface CommonSchema {

    Object valueByHeader(String headerName, Object originalEntity) throws Exception;

    Object valueByHeader(String headerName, Object originalEntity, boolean newRow) throws Exception;
}
