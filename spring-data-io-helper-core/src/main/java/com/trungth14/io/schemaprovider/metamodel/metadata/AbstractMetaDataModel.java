package com.trungth14.io.schemaprovider.metamodel.metadata;

import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;

import java.lang.reflect.Field;
import java.util.function.Function;

public abstract class AbstractMetaDataModel implements MetaData {

    protected Field metaField;
    protected Class<?> assignableClass;


    public AbstractMetaDataModel(Field metaField, Class<?> assignableClass) {
        this.metaField = metaField;
        this.assignableClass = assignableClass;

    }

    public String[] getMetaDataDefinition() {
        SchemaDefinition columnName = this.metaField.getDeclaredAnnotation(SchemaDefinition.class);
        return columnName.headerNames();
    }

    @Override
    public Object getMetaDataValueFrom(Object originalObject) throws
            Exception {
        if (originalObject == null) return null;
        if (!originalObject.getClass().isAssignableFrom(this.assignableClass)) {
            throw new IllegalStateException("Owned class instance of type : " + this.assignableClass.getSimpleName()
                    + "should be passed to a Joined type ");
        }
        return this.metaField.get(originalObject);
    }

    @Override
    public Object getMetaDataValueFrom(Object originalObject, Function<Object, Object> appliedFunc) throws Exception {
        return appliedFunc.apply(this.getMetaDataValueFrom(originalObject));
    }
}
