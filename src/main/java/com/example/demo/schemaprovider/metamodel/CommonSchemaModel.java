package com.example.demo.schemaprovider.metamodel;

import com.example.demo.schemaprovider.EntityMetaParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonSchemaModel {

    protected Map<String, MetaDataModel> pathMapping = new HashMap<>();
    private Class<?> reflectedClass;


    public CommonSchemaModel(Class<?> reflectedClass, List<MetaDataModel> metaDataModels) {
        this.reflectedClass = reflectedClass;
        metaDataModels.forEach(metaDataModel -> {
            pathMapping.put(metaDataModel.getPath(), metaDataModel);
        });
    }

    public MetaDataModel getRelatedMetaDataBy(String pathName)
    {
        return this.pathMapping.getOrDefault(pathName, null);
    }

    public String getRelationBy(String pathName) {
        return this.getRelatedMetaDataBy(pathName).getPath();
    }

}
