package com.trungth14.io.export;

import com.trungth14.io.schemaprovider.ExternalValueProvider;
import com.trungth14.io.schemaprovider.metamodel.CommonSchema;
import com.trungth14.io.schemaprovider.metamodel.MappedSchema;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public final class ExcelFileExporter implements ExcelExportable {

    protected final CommonSchema mappedSchema;
    protected final ExportProperty exportProperty;


    public ExcelFileExporter(Class<?> fromClass,
                             ExportProperty exportProperty) throws IllegalAccessException
    {
        this.exportProperty = exportProperty;
        this.mappedSchema = MappedSchema.from(fromClass, Collections::emptyMap);
    }


    public ExcelFileExporter(Class<?> fromClass,
                             ExportProperty exportProperty,
                             ExternalValueProvider externalValueProvider) throws IllegalAccessException
    {
        this.exportProperty = exportProperty;
        this.mappedSchema = MappedSchema.from(fromClass, externalValueProvider);
    }


    @Override
    public void export(List<?> fromCollection, OutputStream toOutputStream)
    {
        try
        {
            Workbook currentWorkBook = new SXSSFWorkbook(100);
            this.prepareWorkbook(fromCollection, currentWorkBook);
            currentWorkBook.write(toOutputStream);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void prepareWorkbook(List<?> fromCollection, Workbook currentWorkBook) throws Exception
    {
        Integer rowPerSheet = exportProperty.getRowPerSheet();
        List<String> expectedHeaders = exportProperty.getExpectedHeaders();
        String sheetPrefix = exportProperty.getSheetPrefix();
        if (shouldRecordsBeSeperated())
        {
            if (fromCollection.size() > rowPerSheet)
            {
                int totalSheets = fromCollection.size() / exportProperty.getRowPerSheet();
                int start = 0;
                int end = exportProperty.getRowPerSheet();
                for (int i = 0; i < totalSheets; i++)
                {
                    List<?> subList = fromCollection.subList(start, end);
                    newSheet(expectedHeaders, subList, sheetPrefix, currentWorkBook, i);
                    start += subList.size();
                    end += subList.size();
                }
            }
            else
            {
                this.newSheet(expectedHeaders, fromCollection, sheetPrefix, currentWorkBook, -1);
            }
        }
        else
        {
            this.newSheet(expectedHeaders, fromCollection, sheetPrefix, currentWorkBook, -1);
        }
    }

    private boolean shouldRecordsBeSeperated()
    {
        return exportProperty.getRowPerSheet() != 0;
    }

    protected void newSheet(List<String> expectedHeaders, List<?> fromCollection,
                            String sheetPrefix, Workbook currentWorkBook,
                            int sheetNumber) throws Exception
    {
        Sheet sheet = this.applySheetName(sheetPrefix, currentWorkBook, sheetNumber);
        this.createHeaderRows(sheet, expectedHeaders);
        // Per record
        boolean isNewRow;
        for (int i = 0; i < fromCollection.size(); i++)
        {
            // Per row
            Object currentRecord = fromCollection.get(i);
            Row row = sheet.createRow(i + 1);
            isNewRow = true;
            Cell currentCell;
            for (int j = 0; j < expectedHeaders.size(); j++)
            {
                currentCell = row.createCell(j);
                String currentHeader = expectedHeaders.get(j);
                Object cellValue = this.mappedSchema.valueByHeader(currentHeader, currentRecord, isNewRow);
                if (cellValue == null)
                {
                    currentCell.setBlank();
                }
                else
                {
                    currentCell.setCellValue(cellValue.toString());
                }
                isNewRow = false;
            }
        }

    }

    private Sheet applySheetName(String sheetPrefix, Workbook currentWorkBook, int sheetNumber)
    {
        Sheet sheet;
        if (!sheetPrefix.isBlank())
        {
            if (sheetNumber == -1)
            {
                sheet = currentWorkBook.createSheet(sheetPrefix);
            }
            else
            {
                sheet = currentWorkBook.createSheet(sheetPrefix + "-" + sheetNumber);
            }
        }
        else
        {
            sheet = currentWorkBook.createSheet();
        }
        return sheet;
    }

    private void createHeaderRows(Sheet sheet, List<String> expectedHeaders)
    {
        Row headerRow = sheet.createRow(0);
        Cell currentCell;
        for (int i = 0; i < expectedHeaders.size(); i++)
        {
            currentCell = headerRow.createCell(i);
            currentCell.setCellValue(expectedHeaders.get(i));
        }
    }


    @Override
    public Workbook export(List<?> fromCollection) throws Exception
    {
        Workbook workbook = new SXSSFWorkbook(100);
        this.prepareWorkbook(fromCollection, workbook);
        return workbook;
    }
}
