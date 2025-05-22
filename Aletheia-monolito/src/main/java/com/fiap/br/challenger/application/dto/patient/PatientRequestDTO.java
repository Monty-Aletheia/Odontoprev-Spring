package com.fiap.br.challenger.application.dto.patient;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {

        @NotBlank(message = "Name is required.")
        private String name;

        @NotNull(message = "Birthday is required.")
        private LocalDate birthday;

        @NotNull(message = "Gender is required.")
        private Gender gender;

        @NotNull(message = "Risk status is required.")
        private RiskStatus riskStatus;

        @Positive(message = "Consultation frequency must be a positive number.")
        private Integer consultationFrequency;

        @NotBlank(message = "Associated claims are required.")
        private String associatedClaims;

        @NotBlank(message = "Email is required")
        @Email
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
}
