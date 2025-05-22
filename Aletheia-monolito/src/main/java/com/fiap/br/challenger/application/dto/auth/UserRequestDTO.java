package com.fiap.br.challenger.application.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password

) {



}
