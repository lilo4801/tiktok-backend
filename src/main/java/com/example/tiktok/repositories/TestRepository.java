package com.example.tiktok.repositories;

import com.example.tiktok.models.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TestRepository extends CrudRepository<Test,Long> {
}
