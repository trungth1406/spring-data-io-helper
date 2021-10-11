package com.trungth14.io.export;

import java.io.OutputStream;
import java.util.List;

public interface StreamExport extends Exportable {

    void export(List<?> fromCollection, OutputStream toOutputStream);
}