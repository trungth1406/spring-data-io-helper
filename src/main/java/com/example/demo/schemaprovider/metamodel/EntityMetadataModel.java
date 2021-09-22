package com.example.demo.schemaprovider.metamodel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EntityMetadataModel implements MetaDataModel {

    protected Field metaField;
    protected Class<?> assignableClass;
    protected List<MetaDataModel> metaDataRelationships;

    public EntityMetadataModel(Field metaField, Class<?> assignableClass) {
        this.metaField = metaField;
        this.assignableClass = assignableClass;
    }

    public String joinMetaDataDefinition() {
        String relationShipMetaDefinition = metaDataRelationships
                .stream()
                .map(MetaDataModel::getMetaDataDefinition)
                .collect(Collectors.joining(","));
        return String.join(".", this.getMetaDataDefinition(), relationShipMetaDefinition);
    }

}
