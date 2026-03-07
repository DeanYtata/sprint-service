package com.catalog.catalog_service.controller;import com.catalog.catalog_service.model.Product;
import com.catalog.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;@RestController
@RequestMapping("/products")
public class ProductController {
@Autowired
private ProductRepository productRepository;

@PostMapping
public ResponseEntity<Product> create(@RequestBody Product product) {
    return ResponseEntity.ok(productRepository.save(product));
}

@GetMapping
public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(productRepository.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Product> getById(@PathVariable Long id) {
    return productRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@PutMapping("/{id}")
public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updated) {
    return productRepository.findById(id).map(product -> {
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        return ResponseEntity.ok(productRepository.save(product));
    }).orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!productRepository.existsById(id)) return ResponseEntity.notFound().build();
    productRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}

@GetMapping("/{id}/check-stock")
public ResponseEntity<String> checkStock(@PathVariable Long id, @RequestParam int quantity) {
    return productRepository.findById(id).map(product -> {
        if (product.getStock() >= quantity) {
            return ResponseEntity.ok("Stock disponible");
        } else {
            return ResponseEntity.status(409).<String>body("Stock insuficiente");
        }
    }).orElse(ResponseEntity.notFound().build());
}
}
