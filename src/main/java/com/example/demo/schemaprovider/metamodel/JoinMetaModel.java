package com.example.demo.schemaprovider.metamodel;

import java.util.List;

public interface JoinMetaModel extends MetaDataModel {

    List<MetaDataModel> getMetaDataDefinition();
}
