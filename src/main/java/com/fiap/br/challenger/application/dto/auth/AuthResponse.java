package com.fiap.br.challenger.application.dto.auth;

public record AuthResponse(
        String token,
        String message
) {
}
