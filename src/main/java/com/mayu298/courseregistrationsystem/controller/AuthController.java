package com.mayu298.courseregistrationsystem.controller;

import com.mayu298.courseregistrationsystem.dto.LoginRequestDTO;
import com.mayu298.courseregistrationsystem.dto.RegisterRequestDTO;
import com.mayu298.courseregistrationsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        authService.register(registerRequestDTO);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequestDTO dto) {

        String token = authService.login(dto);

        return ResponseEntity.ok(token);
    }

}
