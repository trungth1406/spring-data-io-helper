package com.trungth14.io.schemaprovider.metamodel;


import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;
import com.trungth14.io.schemaprovider.metamodel.metadata.BasicMetaData;
import com.trungth14.io.schemaprovider.metamodel.metadata.JoinMetaData;
import com.trungth14.io.schemaprovider.metamodel.metadata.MetaData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetaDataParser {

    public static List<MetaData> parse(Class<?> clazz) {
        List<MetaData> schemaModels = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(SchemaDefinition.class)) {
                SchemaDefinition schemaDefinition = field.getDeclaredAnnotation(SchemaDefinition.class);
                if (schemaDefinition.isJoined()) {
                    Class<?> baseClass = field.getType();
                    Class<?> refJoinedClass = schemaDefinition.refJoinedClass();
                    if (isDefaultJavaClass(baseClass) && !isRefJoinedClassNotDefault(refJoinedClass)) {
                        throw new IllegalArgumentException("Joined field " + field.getName() + "should provide a reference class");
                    } else if (isRefJoinedClassNotDefault(refJoinedClass)) {
                        clazz = refJoinedClass;
                    }
                    schemaModels.add(new JoinMetaData(field, baseClass, clazz));
                } else {
                    schemaModels.add(new BasicMetaData(field, clazz));
                }
            }
        }
        return schemaModels;
    }

    private static boolean isRefJoinedClassNotDefault(Class<?> refJoinedClass) {
        return !refJoinedClass.getName().equals("java.lang.Object");
    }

    private static boolean isDefaultJavaClass(Class<?> refJoinedClass) {
        return refJoinedClass.getName().contains("java.lang");
    }
}

