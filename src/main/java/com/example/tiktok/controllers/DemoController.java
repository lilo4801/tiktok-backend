package com.example.tiktok.controllers;

import com.example.tiktok.repositories.RoleRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@RestController
public class DemoController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;


    private static final Logger LOGGER = LogManager.getLogger(DemoController.class);


    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return LanguageUtils.getMessage("greeting");
    }

    @GetMapping("/test")
    public String testException(HttpServletRequest request) {
        LOGGER.info("Running test");
        return "sualinhtinh1233";

    }


}
