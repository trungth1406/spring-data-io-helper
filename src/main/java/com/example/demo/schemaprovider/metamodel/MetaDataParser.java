package com.example.demo.schemaprovider.metamodel;


import com.example.demo.schemaprovider.annotation.SchemaDefinition;
import com.example.demo.schemaprovider.metadata.BasicMetaData;
import com.example.demo.schemaprovider.metadata.ExportMetaData;
import com.example.demo.schemaprovider.metadata.JoinMetaData;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataParser {

    @SneakyThrows
    public static List<ExportMetaData> parse(Class<?> clazz)  {
        List<ExportMetaData> schemaModels = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SchemaDefinition.class)) {
                SchemaDefinition schemaDefinition = field.getDeclaredAnnotation(SchemaDefinition.class);
                if (schemaDefinition.isJoined()) {
                    Class<?> baseClass = field.getType();
                    Class<?> refJoinedClass = schemaDefinition.refJoinedClass();
                    if (isRefJoinedClassNotDefault(refJoinedClass)) {
                        List<ExportMetaData> parsed = parse(refJoinedClass);
                        schemaModels.addAll(parsed);
                    } else {
                        schemaModels.add(new JoinMetaData(baseClass, clazz, field));
                    }
                } else if (isDecoratorClassNotDefault(schemaDefinition)) {
                    BasicMetaData joinMetaData = new BasicMetaData(clazz, field);
                    Class<?> decoratorClass = schemaDefinition.refDecorator();
                    try {
                        Constructor<?> constructor = decoratorClass.getConstructor(ExportMetaData.class);
                        Object o = constructor.newInstance(joinMetaData);
                        schemaModels.add((ExportMetaData) o);
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        throw new IllegalAccessException("Unable to wrap with decorated class");
                    }
                } else {
                    schemaModels.add(new BasicMetaData(clazz, field));
                }
            }
        }
        return schemaModels;
    }

    private static boolean isRefJoinedClassNotDefault(Class<?> refJoinedClass) {
        return !refJoinedClass.getName().equals("java.lang.Object");
    }

    private static boolean isDecoratorClassNotDefault(SchemaDefinition schemaDefinition) {
        return !schemaDefinition.refDecorator().getName().equals("java.lang.Object");
    }


}

