package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.ClaimType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "occurrence_date", nullable = false)
    private LocalDate occurrenceDate;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_type", nullable = false)
    private ClaimType claimType;

    @Lob
    @Column(name = "suggested_preventive_action")
    private String suggestedPreventiveAction;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;
}
