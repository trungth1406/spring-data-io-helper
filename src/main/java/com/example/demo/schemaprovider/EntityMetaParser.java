package com.example.demo.schemaprovider;

import com.example.demo.schemaprovider.annotation.JoinType;
import com.example.demo.schemaprovider.annotation.JoinedSchemaDefinition;
import com.example.demo.schemaprovider.annotation.SchemaDefinition;
import com.example.demo.schemaprovider.metamodel.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityMetaParser {

    public static CommonSchemaModel convert(Class<?> clazz) {
        return new CommonSchemaModel(clazz, parse(clazz));
    }

    private static void checkEntityClass(Class<?> clazz) {
        if (clazz.getDeclaredAnnotation(Entity.class) == null) {
            throw new IllegalStateException("Not a entity type");
        }
    }

    private static List<MetaDataModel> parse(Class<?> clazz) {
        checkEntityClass(clazz);
        MetaDataModel root = null;
        List<MetaDataModel> schemaModels = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SchemaDefinition.class)) {
                root = new JavaTypeMetaModel(clazz, field);
                schemaModels.add(root);
            } else if (field.isAnnotationPresent(JoinedSchemaDefinition.class)) {
                if (root == null) {
                    throw new IllegalStateException("Need a root meta data model");
                }
                JoinedSchemaDefinition joinedSchema = field.getDeclaredAnnotation(JoinedSchemaDefinition.class);
                if (joinedSchema.joinType() == JoinType.ONE_TO_ONE) {
                    Class<?> type = field.getType();
                    schemaModels.add(new OneToOneMetaModel(root, field, type));
                }
            }
        }
        return schemaModels;
    }


}
