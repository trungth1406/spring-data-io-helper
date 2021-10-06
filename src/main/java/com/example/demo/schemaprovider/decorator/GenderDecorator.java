package com.example.demo.schemaprovider.decorator;


import com.example.demo.schemaprovider.metadata.ExportMetaData;

public class GenderDecorator extends BaseDecorator {

    public GenderDecorator(ExportMetaData rootMeta) {
        super(rootMeta);
    }

    @Override
    protected Class<?> expectedType() {
        return Integer.class;
    }

    @Override
    protected Object decoratedValue(Object originalValue) {
        return (Integer) originalValue == 1 ? "Nam" : "Nữ";
    }


}
