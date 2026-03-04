package com.catalog.catalog_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}/check-stock")
    public ResponseEntity<Boolean> checkStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        if (quantity <= 10) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}