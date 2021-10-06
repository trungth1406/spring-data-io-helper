package com.example.demo.schemaprovider.metamodel;

import java.util.*;
import java.util.stream.Stream;

public class CommonSchemaModel {

    protected Map<String[], MetaDataModel> pathMapping = new HashMap<>();
    private Class<?> reflectedClass;


    public CommonSchemaModel(Class<?> reflectedClass, List<MetaDataModel> metaDataModels) {
        this.reflectedClass = reflectedClass;
        metaDataModels.forEach(metaDataModel -> {
            pathMapping.put(metaDataModel.getMetaDataDefinition(), metaDataModel);
        });
    }

    public List<String[]> getPaths() {
        return new ArrayList<>(this.pathMapping.keySet());
    }

    public Object[] getColumnsName() {
        this.pathMapping
                .values()
                .stream()
                .map(MetaDataModel::getMetaDataDefinition)
                .forEach(strings -> System.out.println(Arrays.toString(strings)));
        return null;
    }

    public Object getValue(String schemaDefinition) {
        return this.pathMapping
                .values()
                .stream()
                .map(MetaDataModel::getMetaDataDefinition);
    }
}
