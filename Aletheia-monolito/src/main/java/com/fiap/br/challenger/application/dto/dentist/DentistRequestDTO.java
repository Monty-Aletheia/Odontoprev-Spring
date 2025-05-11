package com.fiap.br.challenger.application.dto.dentist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistRequestDTO {

        @NotBlank(message = "Name is required.")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
        private String name;

        @NotBlank(message = "Specialty is required.")
        @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters.")
        private String specialty;

        @NotBlank(message = "Registration number is required.")
        @Size(min = 1, max = 20, message = "Registration number must be between 1 and 20 characters.")
        private String registrationNumber;

        @NotBlank(message = "Password is required.")
        private String password;

        @NotBlank(message = "Email is required")
        @Email
        private String email;
}
