package com.trungth14.io.export;

import com.trungth14.io.schemaprovider.ExternalValueProvider;
import com.trungth14.io.schemaprovider.metamodel.CommonSchema;
import com.trungth14.io.schemaprovider.metamodel.MappedSchema;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.List;

public abstract class ExcelFileExporter implements StreamExport {

    protected final CommonSchema mappedSchema;
    protected final ExternalValueProvider externalValueProvider;
    protected final ExportProperty exportProperty;

    protected ExcelFileExporter(Class<?> fromClass,
                                ExternalValueProvider externalValueProvider,
                                ExportProperty exportProperty) throws IllegalAccessException {
        this.exportProperty = exportProperty;
        this.mappedSchema = MappedSchema.from(fromClass, externalValueProvider);
        this.externalValueProvider = externalValueProvider;
    }

    protected abstract Workbook newWorkBook();

    @Override
    public void export(List<?> fromCollection, OutputStream toOutputStream) {
        Workbook currentWorkBook = this.newWorkBook();
        Integer rowPerSheet = exportProperty.getRowPerSheet();
        List<String> expectedHeaders = exportProperty.getExpectedHeaders();
        String sheetPrefix = exportProperty.getSheetPrefix();
        if (exportProperty.getRowPerSheet() != 0) {
            if (fromCollection.size() <= rowPerSheet) {
                int totalSheets = fromCollection.size() / exportProperty.getRowPerSheet();
            } else {
                this.newSheet(expectedHeaders, fromCollection, sheetPrefix, currentWorkBook);
            }

        }

    }

    private void newSheet(List<String> expectedHeaders, List<?> fromCollection,
                          String sheetPrefix, Workbook currentWorkBook) {
        Sheet sheet = currentWorkBook.createSheet();
        this.createHeaderRows(sheet, expectedHeaders);
        for (int i = 0; i < expectedHeaders.size(); i++) {

        }
    }

    private void createHeaderRows(Sheet sheet, List<String> expectedHeaders) {
        Row headerRow = sheet.createRow(0);
        Cell currentCell;
        for (int i = 0; i < expectedHeaders.size(); i++) {
            currentCell = headerRow.createCell(i);
            currentCell.setCellValue(expectedHeaders.get(i));
        }
    }


    @Override
    public void export(List<?> fromCollection) {

    }
}
