package com.example.demo.schemaprovider;

import com.example.demo.schemaprovider.annotation.SchemaDefinition;
import com.example.demo.schemaprovider.metamodel.OriginalEntityMetaModel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityMetaParser<T extends Serializable> implements MetaDataParsable<T> {

    @Override
    public List<OriginalEntityMetaModel> parse(Class<T> clazz) {
        this.checkEntityClass(clazz);
        List<OriginalEntityMetaModel> schemaModels = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(SchemaDefinition.class)) {
                field.setAccessible(true);
                schemaModels.add(new OriginalEntityMetaModel(clazz, field));
            }
        }
        return null;
    }


}
