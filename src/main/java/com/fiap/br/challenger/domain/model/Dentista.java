package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.StatusRisco;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Dentista")
@Data
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String especialidade;

    @Column(name = "número_registro", unique = true, nullable = false)
    private String númeroRegistro;

    @Column(name = "taxa_sinistro", columnDefinition = "DECIMAL(5, 2) DEFAULT 0.00")
    private Double taxaSinistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_risco", nullable = false)
    private StatusRisco statusRisco;
}