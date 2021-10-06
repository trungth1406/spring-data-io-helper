package com.example.demo.schemaprovider.decorator;

import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;
import com.mbal.leadservice.util.constant.Channel;

public class ChannelDecorator extends BaseDecorator {

    public ChannelDecorator(ExportMetaData rootMeta) {
        super(rootMeta);
    }

    @Override
    protected Class<?> expectedType() {
        return Integer.class;
    }

    @Override
    protected Object decoratedValue(Object originalValue) {
        return Channel.convertIdToName(String.valueOf(originalValue));
    }
}
