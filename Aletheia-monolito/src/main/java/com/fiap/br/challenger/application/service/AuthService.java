package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.auth.AuthResponse;
import com.fiap.br.challenger.application.dto.auth.LoginDTO;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final DentistRepository dentistRepository;

    public AuthResponse authenticate(LoginDTO authRequest) {
        Optional<Dentist> userOptional = dentistRepository.findByRegistrationNumber(authRequest.registrationNumber());

        if (userOptional.isPresent()) {
            Dentist user = userOptional.get();
            if (user.getPassword().equals(authRequest.password())) {
                String sessionToken = "session-token-" + user.getId();
                return new AuthResponse(sessionToken, "Autenticação bem-sucedida");
            }
        }

        return new AuthResponse(null, "Credenciais inválidas");
    }
}
