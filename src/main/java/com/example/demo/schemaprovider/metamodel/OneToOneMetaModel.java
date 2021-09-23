package com.example.demo.schemaprovider.metamodel;

import com.example.demo.schemaprovider.EntityMetaParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OneToOneMetaModel extends EntityMetadataModel implements JoinMetaModel {

    private final MetaDataModel root;

    public OneToOneMetaModel(MetaDataModel root, Field metaField, Class<?> assignableClass) {
        super(metaField, assignableClass);
        this.root = root;
    }


    @Override
    public String getPath() {
        return String.join(".", this.root.getPath(), super.getPath());
    }

    @Override
    public List<MetaDataModel> getMetaDataDefinition() {
        CommonSchemaModel convert = EntityMetaParser.convert(this.assignableClass);
        return new ArrayList<>(convert.pathMapping.values());
    }
}
