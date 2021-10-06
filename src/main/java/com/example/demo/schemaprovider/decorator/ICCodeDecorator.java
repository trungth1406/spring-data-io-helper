package com.example.demo.schemaprovider.decorator;


import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;

import java.lang.reflect.InvocationTargetException;

public class ICCodeDecorator implements ExportMetaData {

    private final ExportMetaData toBeDecorated;

    public ICCodeDecorator(ExportMetaData toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }



    @Override
    public String[] getMetaDataDefinition() {
        return toBeDecorated.getMetaDataDefinition();
    }

    @Override
    public Object getMetaDataValueFrom(Object originalObject) throws
            IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object metaDataValueFrom = toBeDecorated.getMetaDataValueFrom(originalObject);


        return null;
    }
}
