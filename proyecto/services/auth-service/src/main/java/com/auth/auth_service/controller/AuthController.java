package com.auth.auth_service.controller;

import com.auth.auth_service.dto.AuthRequest;
import com.auth.auth_service.model.User;
import com.auth.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        User user = new User();
        user.username = request.username;
        user.password = request.password;
        user.role = request.role;
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return authService.login(request.username, request.password);
    }
}