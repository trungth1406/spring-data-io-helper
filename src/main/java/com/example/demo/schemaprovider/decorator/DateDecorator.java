package com.example.demo.schemaprovider.decorator;


import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateDecorator extends BaseDecorator {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DateDecorator(ExportMetaData rootMeta) {
        super(rootMeta);
    }

    @Override
    protected Class<?> expectedType() {
        return LocalDateTime.class;
    }

    @Override
    protected Object decoratedValue(Object originalValue) {
        return dateTimeFormatter.format((TemporalAccessor) originalValue);
    }
}
