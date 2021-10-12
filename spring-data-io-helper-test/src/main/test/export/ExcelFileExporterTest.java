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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@PropertySource("classpath:application.properties")
class ExcelFileExporterTest {

    @Autowired
    MockMvc mockMvc;


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
                new ExcelFileExporter(User.class, exportProperty, valueProvider);

        List<User> allUsers = userRepository.findAll();

        try (FileOutputStream os = new FileOutputStream(new File("demo.xlsx"))) {
            streamExport.export(allUsers, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mocking_export() throws Exception {
        this.mockMvc.perform(get("/api/v1/users")).andDo(mvcResult -> {
            MockHttpServletResponse response = mvcResult.getResponse();
            Collection<String> headerNames = response.getHeaderNames();
            System.out.println(headerNames);
        });
    }
}