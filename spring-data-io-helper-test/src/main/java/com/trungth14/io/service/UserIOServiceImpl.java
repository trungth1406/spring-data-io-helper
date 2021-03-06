package com.trungth14.io.service;

import com.trungth14.io.entity.User;
import com.trungth14.io.export.ExcelFileExporter;
import com.trungth14.io.export.ExportProperty;
import com.trungth14.io.export.FileType;
import com.trungth14.io.export.StreamExport;
import com.trungth14.io.repository.UserRepository;
import com.trungth14.io.schemaprovider.metamodel.MappedSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class UserIOServiceImpl implements UserIOService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void export(HttpServletResponse servletResponse)
    {
        try
        {
            List<User> allUser = userRepository.findAll();
            ExportProperty exportProperty =
                    ExportProperty.ExportPropertyBuilder
                            .anExportProperty()
                            .withExpectedHeaders(Arrays.asList("ID", "Tên người dùng", "Chức danh người dùng", "Tuổi"))
                            .withFileType(FileType.XLSX)
                            .withSheetPrefix("Demo")
                            .build();
            StreamExport streamExport = new ExcelFileExporter(User.class, exportProperty);
            streamExport.export(allUser, servletResponse.getOutputStream());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
