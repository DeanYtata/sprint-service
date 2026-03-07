package com.auth.auth_service.dto;

public class AuthRequest {
    public String username;
    public String password;
    public String role; // Usado solo en registro (ADMIN o CUSTOMER)
}