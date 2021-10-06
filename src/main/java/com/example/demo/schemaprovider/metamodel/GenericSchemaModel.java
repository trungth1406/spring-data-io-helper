package com.example.demo.schemaprovider.metamodel;


import com.example.demo.schemaprovider.metadata.ExportMetaData;
import com.example.demo.schemaprovider.metadata.JoinMetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GenericSchemaModel implements CommonSchema {

    private final Map<String, ExportMetaData> pathMapping = new HashMap<>();
    private final Map<String, Function<Object, Object>> externalValuePathMapping = new HashMap<>();
    private final Map<Function<Object, Object>, Object> cachedExternalValue = new HashMap<>();

    private GenericSchemaModel(Class<?> fromClass, Map<List<String>, Function<Object, Object>> externalValuePathMapping) throws IllegalAccessException {
        this.performPathMapping(fromClass);
        for (List<String> definitions : externalValuePathMapping.keySet()) {
            for (String s : definitions) {
                this.externalValuePathMapping.put(s, externalValuePathMapping.get(definitions));
            }
        }
    }

    public static GenericSchemaModel from(Class<?> clazz, Map<List<String>, Function<Object, Object>> schemaMapping) throws IllegalAccessException {
        return new GenericSchemaModel(clazz, schemaMapping);
    }

    private void performPathMapping(Class<?> clazz) throws IllegalAccessException {
        List<ExportMetaData> metaDataList = MetaDataParser.parse(clazz);
        for (ExportMetaData exportMetaData : metaDataList) {
            for (String definition : exportMetaData.getMetaDataDefinition()) {
                this.pathMapping.put(definition, exportMetaData);
            }
        }
    }

    public Object valueByHeader(String columnName, Object originalObject)
            throws Exception {
        return this.valueByHeader(columnName, originalObject, true);
    }

    // A little optimization for reuse external Value mapping
    @Override
    public Object valueByHeader(String columnName, Object originalObject, boolean newRow) throws Exception {
        ExportMetaData metaData = this.pathMapping.get(columnName);
        if (metaData == null) return null;
        Function<Object, Object> callBackFunc = this.externalValuePathMapping.get(columnName);
        if (newRow) {
            cachedExternalValue.clear();
        }

        if (!newRow && callBackFunc != null) {
            Object cachedValue = cachedExternalValue.get(callBackFunc);
            if (cachedValue != null) {
                return metaData.getMetaDataValueFrom(cachedValue);
            } else {
                Object proxiedValue = callBackFunc.apply(originalObject);
                cachedExternalValue.put(callBackFunc, proxiedValue);
                if (proxiedValue == null) return null;
                return metaData.getMetaDataValueFrom(proxiedValue);
            }
        } else if (callBackFunc != null) {
            Object proxiedValue = callBackFunc.apply(originalObject);
            cachedExternalValue.put(callBackFunc, proxiedValue);
            if (proxiedValue == null) return null;
            return metaData.getMetaDataValueFrom(proxiedValue);
        }

        if (metaData instanceof JoinMetaData) {
            Object ownedObject = metaData.getMetaDataValueFrom(originalObject);
            if (ownedObject == null) return null;
            CommonSchema originalSchema = ((JoinMetaData) metaData).getOriginalSchema();
            return originalSchema.valueByHeader(columnName, ownedObject);
        }
        return metaData.getMetaDataValueFrom(originalObject);
    }


}
