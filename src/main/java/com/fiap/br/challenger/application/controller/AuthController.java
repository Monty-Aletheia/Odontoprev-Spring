package com.fiap.br.challenger.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/signIn")
    public ResponseEntity<?> SignIn(){
        // TODO fazer rota de login do dentista
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(){
        // TODO fazer rota de cadastro do dentista
        return ResponseEntity.ok().build();
    }
}
