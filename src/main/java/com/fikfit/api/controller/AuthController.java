package com.fikfit.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.fikfit.api.entity.UserEntity;
import com.fikfit.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    private static final String JWT_SECRET = "fikfit_secret_key_12345678901234567890123456789012"; // 32+ chars, only letters/numbers
    private static final long JWT_EXPIRATION_MS = 86400000; // 1 dia

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.findByUsername(loginRequest.getUsername())
                .filter(user -> user.getPassword().equals(loginRequest.getPassword()))
                .<ResponseEntity<?>>map(user -> {
                    String token = Jwts.builder()
                            .setSubject(user.getUsername())
                            .claim("role", user.getRole())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                            .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                            .compact();
                    return ResponseEntity.ok(new JwtResponse(token));
                })
                .orElse(ResponseEntity.status(401).body("Invalid username or password"));
    }

    // create login request DTO
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        userService.save(user);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    static class LoginRequest {
        private String username;
        private String password;
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class JwtResponse {
        private String token;
        public JwtResponse(String token) { this.token = token; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }

}