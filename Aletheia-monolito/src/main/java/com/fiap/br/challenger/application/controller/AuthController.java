package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.auth.AuthResponse;
import com.fiap.br.challenger.application.dto.auth.LoginDTO;
import com.fiap.br.challenger.application.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginDTO loginDTO) {

        // TODO adicionar JWT depois
        AuthResponse authResponse = authService.authenticate(loginDTO);

        if (authResponse != null) {
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

}
