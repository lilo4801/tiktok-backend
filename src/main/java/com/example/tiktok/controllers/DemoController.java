package com.example.tiktok.controllers;

import com.example.tiktok.entities.Role;
import com.example.tiktok.entities.User;
import com.example.tiktok.models.Test;
import com.example.tiktok.repositories.RoleRepository;
import com.example.tiktok.repositories.TestRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class DemoController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return LanguageUtils.getMessage("greeting");
    }

    @GetMapping("/test")
    public String testException(HttpServletRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("test");

        User newUser = new User("test@test.com", password);
        User savedUser = repo.save(newUser);
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_EDITOR");
        Role customer = new Role("ROLE_CUSTOMER");

        roleRepository.save(admin);
        roleRepository.save(editor);
        roleRepository.save(customer);


        return "ok";

    }

    @PostMapping("/insert-test")
    public String insertTest(@RequestParam String content) {

        try {
            Test test = new Test();
            test.setContent(content);
            testRepository.save(test);
        } catch (Exception e) {
            return "insert failure !";
        }

        return "insert sucessfully !";
    }
}
