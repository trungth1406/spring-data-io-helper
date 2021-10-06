package com.example.demo.schemaprovider.decorator;


import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;

public class LeadTypeDecorator extends BaseDecorator {

    public LeadTypeDecorator(ExportMetaData rootMeta) {
        super(rootMeta);
    }

    @Override
    protected Class<?> expectedType() {
        return Integer.class;
    }

    @Override
    protected Object decoratedValue(Object originalValue) {
        return (Integer) originalValue == 1 ? "KHCN" : "KHDN";
    }

}
