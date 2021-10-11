package com.trungth14.io.controller;

import com.trungth14.io.service.UserIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserIOService userIOService;

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse servletResponse) {
        servletResponse.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        servletResponse.setContentType("application/octet-stream");
        userIOService.export(servletResponse);
    }
}
