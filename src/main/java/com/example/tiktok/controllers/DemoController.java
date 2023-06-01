package com.example.tiktok.controllers;

import com.example.tiktok.models.Test;
import com.example.tiktok.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private TestRepository testRepository;
    @GetMapping("/home")
    public String home() {
        return "hello!";
    }

    @PostMapping("/insert-test")
    public String insertTest(@RequestParam String content) {

        try {
            Test test =new Test();
            test.setContent(content);
            testRepository.save(test);
        }catch (Exception e){
            return "insert failure !";
        }

        return "insert sucessfully !";
    }
}
