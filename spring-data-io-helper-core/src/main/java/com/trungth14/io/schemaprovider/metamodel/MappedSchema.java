package com.trungth14.io.schemaprovider.metamodel;


import com.trungth14.io.schemaprovider.ExternalValueProvider;
import com.trungth14.io.schemaprovider.metamodel.metadata.MetaData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class MappedSchema implements CommonSchema {

    private final Map<String, MetaData> pathMapping = new HashMap<>();
    private final Map<String, Function<Object, Object>> externalValuePathMapping = new HashMap<>();
    private final Map<Function<Object, Object>, Object> cachedExternalValue = new HashMap<>();

    private MappedSchema(Class<?> aClass, Map<List<String>, Function<Object, Object>> externalValuePathMapping) throws IllegalAccessException {
        this.performPathMapping(aClass);
        this.externalCallBackMapping(externalValuePathMapping);
    }

    private void performPathMapping(Class<?> clazz) {
        List<MetaData> metaDataList = MetaDataParser.parse(clazz);
        for (MetaData metaData : metaDataList) {
            for (String definition : metaData.getMetaDataDefinition()) {
                this.pathMapping.put(definition, metaData);
            }
        }
    }

    private void externalCallBackMapping(Map<List<String>, Function<Object, Object>> externalValuePathMapping) {
        for (List<String> definitions : externalValuePathMapping.keySet()) {
            for (String s : definitions) {
                this.externalValuePathMapping.put(s, externalValuePathMapping.get(definitions));
            }
        }
    }

    public static MappedSchema from(Class<?> aClass, ExternalValueProvider externalValueProvider) throws IllegalAccessException {
        return new MappedSchema(aClass, externalValueProvider.valueMapper());
    }

    public static MappedSchema from(Class<?> aClass) throws IllegalAccessException {
        return new MappedSchema(aClass, Collections.emptyMap());
    }


    @Override
    public Object valueByHeader(String columnName, Object originalObject)
            throws Exception {
        return this.valueByHeader(columnName, originalObject, true);
    }

    // A little optimization for reuse external Value mapping
    @Override
    public Object valueByHeader(String headerName, Object originalObject, boolean newRow) throws Exception {
        // Get associated meta data representation
        MetaData metaData = this.pathMapping.get(headerName);
        if (metaData == null) return null;
        if (newRow) {
            cachedExternalValue.clear();
        }
        Function<Object, Object> callBackFunc = this.externalValuePathMapping.get(headerName);
        if (callBackFunc != null) {
            return applyExternalValueMapping(originalObject, callBackFunc, metaData, newRow);
        }
        return metaData.getMetaDataValueFrom(originalObject);
    }

    private Object applyExternalValueMapping(Object originalObject, Function<Object, Object> callBackFunc,
                                             MetaData metaData, boolean isNewRow) throws Exception {
        Object cachedValue = cachedExternalValue.get(callBackFunc);
        if (!isNewRow) {
            return cachedValue == null ? null : metaData.getMetaDataValueFrom(cachedValue);
        }
        Object proxiedValue = callBackFunc.apply(originalObject);
        cachedExternalValue.put(callBackFunc, proxiedValue);
        if (proxiedValue == null) return null;
        return metaData.getMetaDataValueFrom(proxiedValue);
    }


}
