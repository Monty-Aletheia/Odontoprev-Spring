package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.Sexo;
import com.fiap.br.challenger.domain.model.enums.StatusRisco;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Paciente")
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_risco", nullable = false)
    private StatusRisco statusRisco;

    @Column(name = "frequência_consultas", columnDefinition = "INT DEFAULT 0")
    private Integer frequênciaConsultas;

    @Lob
    @Column(name = "sinistros_associados")
    private String sinistrosAssociados;

}

