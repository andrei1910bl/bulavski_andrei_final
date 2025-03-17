package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String identifier, @RequestParam String password) {
        UserDTO userDTO = authService.login(identifier, password);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.status(401).build();
    }
}