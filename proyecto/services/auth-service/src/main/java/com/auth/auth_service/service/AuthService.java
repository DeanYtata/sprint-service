package com.auth.auth_service.service;

import com.auth.auth_service.model.User;
import com.auth.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public JwtService jwtService;

    public String register(User user) {
        // Encriptamos la clave antes de guardar
        user.password = passwordEncoder.encode(user.password);
        userRepository.save(user);
        return "Usuario registrado con éxito";
    }

    public String login(String username, String password) {
        // 1. Buscamos al usuario por nombre o lanzamos error si no existe
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Comparamos la clave ingresada con la encriptada en la base de datos
        if (passwordEncoder.matches(password, user.password)) {
            // 3. Si es correcta, generamos y devolvemos el token JWT
            return jwtService.generateToken(user.username, user.role);
        } else {
            // 4. Si la clave no coincide, lanzamos error
            throw new RuntimeException("Credenciales incorrectas");
        }
    }
}