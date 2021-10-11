package com.trungth14.io.export;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExcelExportable extends StreamExport {

    Workbook export(List<?> fromCollection) throws Exception;
}
