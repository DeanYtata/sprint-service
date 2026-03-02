package com.auth.auth_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String username;

    public String password;

    public String role; // ADMIN o CUSTOMER
}