package com.example.demo.schemaprovider;

import com.example.demo.schemaprovider.metamodel.CommonSchemaModel;

public interface MetaDataParsable<T> {

    CommonSchemaModel parse(Class<T> type);


}
