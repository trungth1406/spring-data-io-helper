package com.example.demo.schemaprovider.metadata;

import com.example.demo.schemaprovider.metamodel.CommonSchema;
import com.example.demo.schemaprovider.metamodel.MetaDataParser;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class JoinMetaData implements ExportMetaData {

    private final Class<?> aClass;
    private final Class<?> ownedClass;
    private final Field refField;

    public JoinMetaData(Class<?> aClass, Class<?> ownedClass, Field refField) {
        this.aClass = aClass;
        this.ownedClass = ownedClass;
        this.refField = refField;
    }


    @Override
    public String[] getMetaDataDefinition() {
        List<ExportMetaData> fromThisClass = MetaDataParser.parse(this.aClass);
        return fromThisClass.stream()
                .map(ExportMetaData::getMetaDataDefinition)
                .reduce((first, second) -> {
                    Arrays.asList(first).addAll(Arrays.asList(second));
                    return first;
                })
                .orElse(new String[0]);

    }

    @Override
    public Object getMetaDataValueFrom(Object originalObject) throws IllegalAccessException {
        if (originalObject == null) return null;
        if (!originalObject.getClass().isAssignableFrom(this.ownedClass)) {
            throw new IllegalStateException("Owned class instance of type : " + this.ownedClass.getSimpleName()
                    + "should be passed to a Joined type ");
        }
        return this.refField.get(originalObject);
    }

    public CommonSchema getOriginalSchema() {
        return null;
    }
}
