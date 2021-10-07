package com.trungth14.io.schemaprovider.metamodel.metadata;

import com.trungth14.io.schemaprovider.metamodel.MetaDataParser;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class JoinMetaData extends AbstractMetaDataModel {

    private final Class<?> ownedClass;

    public JoinMetaData(Field metaField, Class<?> assignableClass, Class<?> ownedClass) {
        super(metaField, assignableClass);
        this.ownedClass = ownedClass;
    }


    @Override
    public String[] getMetaDataDefinition() {
        String[] expectedHeaders = super.getMetaDataDefinition();
        List<MetaData> fromThisClass = MetaDataParser.parse(this.assignableClass);
        return fromThisClass
                .stream()
                .map(MetaData::getMetaDataDefinition)
                .reduce(ArrayUtils::addAll)
                .map(strings -> {
                    if (expectedHeaders.length == 0) return strings;
                    Arrays.asList(expectedHeaders).retainAll(Arrays.asList(strings));
                    return expectedHeaders;
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
        this.metaField.setAccessible(true);
        return this.metaField.get(originalObject);
    }
}
