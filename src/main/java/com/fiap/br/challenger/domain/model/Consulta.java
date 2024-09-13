package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.StatusRisco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Consulta")
@Data
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dentista_id", nullable = false)
    private Dentista dentista;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "valor_consulta", nullable = false, columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double valorConsulta;

    @Column(name = "sinistro")
    private Boolean sinistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_risco", nullable = false)
    private StatusRisco statusRisco;
}