package com.example.tiktok.controllers;

import com.example.tiktok.models.requests.users.UploadImageRequest;
import com.example.tiktok.models.responses.FileUploadResponse;
import com.example.tiktok.repositories.RoleRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.services.UserImageService;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
public class DemoController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserImageService userImageService;

    private static final Logger LOGGER = LogManager.getLogger(DemoController.class);


    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return LanguageUtils.getMessage("greeting");
    }

    @GetMapping("/test")
    public String testException(HttpServletRequest request) {
        LOGGER.info("Running test");
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("test");
//
//        User newUser = new User("test@test.com", password);
//        User savedUser = repo.save(newUser);
//        Role admin = new Role("ROLE_ADMIN");
//        Role editor = new Role("ROLE_EDITOR");
//        Role customer = new Role("ROLE_CUSTOMER");
//
//        roleRepository.save(admin);
//        roleRepository.save(editor);
//        roleRepository.save(customer);
        LOGGER.info("Insert into db");
        LOGGER.info("done");

        return "sualinhtinh1233";

    }


}
