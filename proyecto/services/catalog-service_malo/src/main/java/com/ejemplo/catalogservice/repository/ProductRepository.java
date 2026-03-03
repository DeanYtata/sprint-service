package com.ejemplo.catalogservice.repository;

import com.ejemplo.catalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}