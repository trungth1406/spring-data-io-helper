package com.trungth14.io;


import com.github.javafaker.Faker;
import com.trungth14.io.export.ExcelFileExporter;
import com.trungth14.io.export.ExportProperty;
import com.trungth14.io.export.FileType;
import com.trungth14.io.export.StreamExport;
import com.trungth14.io.schemaprovider.ExternalValueProvider;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class ExcelFileExporterTest {
    @Test
    void check_export_to_a_file() throws IllegalAccessException {

        ExternalValueProvider valueProvider = HashMap::new;
        ExportProperty exportProperty = ExportProperty.ExportPropertyBuilder
                .anExportProperty()
                .withExpectedHeaders(Arrays.asList("ID", "Tên người dùng", "Chức danh người dùng", "Tuổi"))
                .withFileType(FileType.XLSX)
                .withSheetPrefix("Demo")
                .build();
        StreamExport streamExport =
                new ExcelFileExporter(User.class, valueProvider, exportProperty);
        Faker faker = new Faker();
        List<User> users = IntStream.range(1, 100_000).mapToObj(i -> {
            User user = new User();
            user.setUserName(faker.funnyName().name());
            user.setUserRole(faker.programmingLanguage().name());
            user.setAge(faker.dog().memePhrase());
            return user;
        }).collect(Collectors.toList());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long startTime = stopWatch.getStartTime();
        try (FileOutputStream os = new FileOutputStream(new File("demo.xlsx"))) {
            streamExport.export(users, os);
            stopWatch.stop();
            long stopTime = stopWatch.getStopTime();
            System.out.println("Total time: " + (stopTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}