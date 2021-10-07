package com.trungth14.io.schemaprovider.metamodel;

public interface CommonSchema {

    Object valueByHeader(String columnName, Object originalObject) throws Exception;

    Object valueByHeader(String currentHeader, Object enhancedLead, boolean newRow) throws Exception;
}