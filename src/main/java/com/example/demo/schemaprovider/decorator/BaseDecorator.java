package com.example.demo.schemaprovider.decorator;

import com.example.demo.schemaprovider.metadata.ExportMetaData;

import java.lang.reflect.InvocationTargetException;

public abstract class BaseDecorator implements ExportMetaData {

    protected final ExportMetaData rootMeta;

    public BaseDecorator(ExportMetaData rootMeta) {
        this.rootMeta = rootMeta;
    }

    protected void validateBaseType(Object object, Class<?> clazz) {
        if (!(clazz.isInstance(object))) {
            throw new IllegalArgumentException("Not a type of: " + clazz.getSimpleName());
        }
    }

    protected abstract Class<?> expectedType();

    protected abstract Object decoratedValue(Object originalValue);

    @Override
    public String[] getMetaDataDefinition() {
        return this.rootMeta.getMetaDataDefinition();
    }

    @Override
    public Object getMetaDataValueFrom(Object originalObject) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object originalValue = this.rootMeta.getMetaDataValueFrom(originalObject);
        if (originalValue == null) return null;
        this.validateBaseType(originalValue, this.expectedType());
        return decoratedValue(originalValue);
    }


}
