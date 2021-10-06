package com.example.demo.schemaprovider.decorator;


import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;
import com.mbal.leadservice.util.constant.Segmentation;

public class SegmentationDecorator extends BaseDecorator {

    public SegmentationDecorator(ExportMetaData rootMeta) {
        super(rootMeta);
    }

    @Override
    protected Class<?> expectedType() {
        return Integer.class;
    }

    @Override
    protected Object decoratedValue(Object originalValue) {
        return Segmentation.convertIdToName(String.valueOf(originalValue));
    }
}
