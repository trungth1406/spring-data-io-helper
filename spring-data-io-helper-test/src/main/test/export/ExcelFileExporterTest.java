package export;


import com.trungth14.io.DemoApplication;
import com.trungth14.io.entity.User;
import com.trungth14.io.export.ExcelFileExporter;
import com.trungth14.io.export.ExportProperty;
import com.trungth14.io.export.FileType;
import com.trungth14.io.export.StreamExport;
import com.trungth14.io.repository.UserRepository;
import com.trungth14.io.schemaprovider.ExternalValueProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@SpringBootTest(classes = DemoApplication.class)
class ExcelFileExporterTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void check_export_to_a_file() throws IllegalAccessException {

        ExternalValueProvider valueProvider = HashMap::new;
        ExportProperty exportProperty = ExportProperty.ExportPropertyBuilder
                .anExportProperty()
                .withExpectedHeaders(Collections.singletonList("Tên người dùng"))
                .withFileType(FileType.XLSX).build();
        StreamExport streamExport =
                new ExcelFileExporter(User.class, valueProvider, exportProperty);

        List<User> allUsers = userRepository.findAll();

        try (FileOutputStream os = new FileOutputStream(new File("demo.xlsx"))) {
            streamExport.export(allUsers, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}