package com.example.demo.schemaprovider.decorator;

import com.mbal.leadservice.service.admin.export.mapper.metadata.ExportMetaData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeDecorator extends BaseDecorator {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public DateTimeDecorator(ExportMetaData rootMeta) {
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
