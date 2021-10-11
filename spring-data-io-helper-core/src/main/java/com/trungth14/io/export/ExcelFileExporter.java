package com.trungth14.io.export;

import com.trungth14.io.schemaprovider.ExternalValueProvider;
import com.trungth14.io.schemaprovider.metamodel.CommonSchema;
import com.trungth14.io.schemaprovider.metamodel.MappedSchema;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class ExcelFileExporter implements ExcelExportable {

    protected final CommonSchema mappedSchema;
    protected final ExportProperty exportProperty;

    public ExcelFileExporter(Class<?> fromClass,
                             ExternalValueProvider externalValueProvider,
                             ExportProperty exportProperty) throws IllegalAccessException {
        this.exportProperty = exportProperty;
        this.mappedSchema = MappedSchema.from(fromClass, externalValueProvider);
    }

    public ExcelFileExporter(Class<?> fromClass,
                             ExportProperty exportProperty) throws IllegalAccessException {
        this.exportProperty = exportProperty;
        this.mappedSchema = MappedSchema.from(fromClass, Collections::emptyMap);
    }

    protected Workbook newWorkBook() {
        switch (this.exportProperty.getFileType()) {
            case XLSX:
                return new SXSSFWorkbook(100);
            default:
                throw new IllegalArgumentException("Type not supported");
        }
    }

    @Override
    public void export(List<?> fromCollection, OutputStream toOutputStream) {
        try {
            Workbook currentWorkBook = this.newWorkBook();
            this.prepareWorkbook(fromCollection, currentWorkBook);
            currentWorkBook.write(toOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void prepareWorkbook(List<?> fromCollection, Workbook currentWorkBook) throws Exception {
        Integer rowPerSheet = exportProperty.getRowPerSheet();
        List<String> expectedHeaders = exportProperty.getExpectedHeaders();
        String sheetPrefix = exportProperty.getSheetPrefix();
        if (exportProperty.getRowPerSheet() != 0) {
            if (fromCollection.size() <= rowPerSheet) {
                int totalSheets = fromCollection.size() / exportProperty.getRowPerSheet();
                int start = 0;
                int end = exportProperty.getRowPerSheet();
                for (int i = 0; i < totalSheets; i++) {
                    List<?> subList = fromCollection.subList(start, end);
                    this.newSheet(expectedHeaders, subList, sheetPrefix, currentWorkBook, i);
                    start += subList.size();
                }
            } else {

            }
        } else {
            this.newSheet(expectedHeaders, fromCollection, sheetPrefix, currentWorkBook, -1);
        }
    }

    protected void newSheet(List<String> expectedHeaders, List<?> fromCollection,
                            String sheetPrefix, Workbook currentWorkBook,
                            int sheetNumber) throws Exception {
        Sheet sheet = this.applySheetName(sheetPrefix, currentWorkBook, sheetNumber);
        this.createHeaderRows(sheet, expectedHeaders);
        // Per record
        boolean isNewRow;
        for (int i = 0; i < fromCollection.size(); i++) {
            // Per row
            Object currentRecord = fromCollection.get(i);
            Row row;
            isNewRow = true;
            for (int j = 0; j < expectedHeaders.size(); j++) {
                row = sheet.createRow(i + 1);
                Cell currentCell = row.createCell(j);
                String currentHeader = expectedHeaders.get(j);
                Object cellValue = this.mappedSchema.valueByHeader(currentHeader, currentRecord, isNewRow);
                if (cellValue == null) {
                    currentCell.setBlank();
                } else {
                    currentCell.setCellValue(cellValue.toString());
                }
                isNewRow = false;
            }
        }

    }

    private Sheet applySheetName(String sheetPrefix, Workbook currentWorkBook, int sheetNumber) {
        Sheet sheet;
        if (!sheetPrefix.isBlank()) {
            if (sheetNumber == -1) {
                sheet = currentWorkBook.createSheet(sheetPrefix);
            } else {
                sheet = currentWorkBook.createSheet(sheetPrefix + "-" + sheetNumber);
            }
        } else {
            sheet = currentWorkBook.createSheet();
        }
        return sheet;
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
    public Workbook export(List<?> fromCollection) throws Exception {
        Workbook workbook = this.newWorkBook();
        this.prepareWorkbook(fromCollection, workbook);
        return workbook;
    }
}
