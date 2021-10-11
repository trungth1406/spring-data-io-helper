package com.trungth14.io.export;

import java.util.Collections;
import java.util.List;


public class ExportProperty {

    private FileType fileType;
    private List<String> expectedHeaders = Collections.emptyList();
    private Integer rowPerSheet = 0;
    private String sheetPrefix = "";

    public FileType getFileType() {
        return fileType;
    }

    public List<String> getExpectedHeaders() {
        return expectedHeaders;
    }

    public Integer getRowPerSheet() {
        return rowPerSheet;
    }

    public String getSheetPrefix() {
        return sheetPrefix;
    }

    public static final class ExportPropertyBuilder {
        private FileType fileType;
        private List<String> expectedHeaders = Collections.emptyList();
        private Integer rowPerSheet = 0;
        private String sheetPrefix;

        private ExportPropertyBuilder() {
        }

        public static ExportPropertyBuilder anExportProperty() {
            return new ExportPropertyBuilder();
        }

        public ExportPropertyBuilder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public ExportPropertyBuilder withExpectedHeaders(List<String> expectedHeaders) {
            this.expectedHeaders = expectedHeaders;
            return this;
        }

        public ExportPropertyBuilder withRowPerSheet(Integer rowPerSheet) {
            this.rowPerSheet = rowPerSheet;
            return this;
        }

        public ExportPropertyBuilder withSheetPrefix(String sheetPrefix) {
            this.sheetPrefix = sheetPrefix;
            return this;
        }

        public ExportProperty build() {
            ExportProperty exportProperty = new ExportProperty();
            exportProperty.rowPerSheet = this.rowPerSheet;
            exportProperty.fileType = this.fileType;
            exportProperty.sheetPrefix = this.sheetPrefix;
            exportProperty.expectedHeaders = this.expectedHeaders;
            return exportProperty;
        }
    }
}
