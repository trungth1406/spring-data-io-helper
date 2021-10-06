package com.example.demo.schemaprovider.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SchemaDefinition {

    String[] headerNames() default "";

    boolean isJoined() default false;

    Class<?> refJoinedClass() default Object.class;

    Class<?> refDecorator() default Object.class;

}
