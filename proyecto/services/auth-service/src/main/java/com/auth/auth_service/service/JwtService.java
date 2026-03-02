package com.auth.auth_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Llave secreta (debe ser larga)
    public String secret = "esta_es_una_clave_secreta_muy_larga_para_el_reto_2024_progra_java";
    public long expiration = 3600000; // 1 hora

    public String generateToken(String username, String role) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Guardamos el rol en el token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
