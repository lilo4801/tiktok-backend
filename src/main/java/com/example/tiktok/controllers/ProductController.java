package com.example.tiktok.controllers;


import com.example.tiktok.entities.Product;
import com.example.tiktok.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repo;

    @PostMapping
    @RolesAllowed("ROLE_EDITOR")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Product savedProduct = repo.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    @GetMapping
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_EDITOR"})
    public List<Product> list() {
        return repo.findAll();
    }
}
