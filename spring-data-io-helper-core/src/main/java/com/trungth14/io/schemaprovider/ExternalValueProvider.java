package com.trungth14.io.schemaprovider;


import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface ExternalValueProvider {

    Map<List<String>, Function<Object,Object>> valueMapper();
}
