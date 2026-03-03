package com.catalog.catalog_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class PingController {

    @GetMapping("/ping")
    public String ping(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return "Catalog Service responde. Correlation ID recibido: " + correlationId;
    }
}