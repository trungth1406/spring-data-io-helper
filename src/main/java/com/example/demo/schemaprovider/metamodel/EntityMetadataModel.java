package com.example.demo.schemaprovider.metamodel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class EntityMetadataModel implements MetaDataModel {

    protected Field metaField;
    protected Class<?> assignableClass;


    public EntityMetadataModel(Field metaField, Class<?> assignableClass) {
        this.metaField = metaField;
        this.assignableClass = assignableClass;

    }
    public String getPath() {
        return this.metaField.getName();
    }

}
