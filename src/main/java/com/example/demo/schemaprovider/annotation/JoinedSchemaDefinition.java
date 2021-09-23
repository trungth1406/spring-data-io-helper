package com.example.demo.schemaprovider.annotation;


import com.sun.xml.bind.v2.schemagen.xmlschema.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinedSchemaDefinition {

    public JoinType joinType() default JoinType.ONE_TO_MANY;

}
