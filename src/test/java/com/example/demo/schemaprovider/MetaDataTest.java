package com.example.demo.schemaprovider;


import com.example.demo.entity.User;
import com.example.demo.schemaprovider.metamodel.CommonSchemaModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MetaDataTest {


    @Test
    void testParsingSchema() {
        CommonSchemaModel convert = EntityMetaParser.convert(User.class);
        for (String[] ss : convert.getPaths()) {
            System.out.println(Arrays.toString(ss));
        }
    }
}
