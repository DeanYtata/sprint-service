package com.ejemplo.catalogservice.controller;

import com.ejemplo.catalogservice.entity.Product;
import com.ejemplo.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // CREATE
    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    // READ - todos
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    // READ - por id
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // 🔥 ENDPOINT DE STOCK
    @GetMapping("/{id}/stock")
    public Integer checkStock(@PathVariable Long id) {
        Product product = service.getById(id);
        return product.getStock();
    }

    // 🔥 ENDPOINT DE DISPONIBILIDAD (boolean)
    @GetMapping("/{id}/availability")
    public boolean isAvailable(@PathVariable Long id) {
        Product product = service.getById(id);
        return product.getStock() > 0;
    }
}