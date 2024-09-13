package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.TipoSinistro;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Sinistro")
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dentista_id", nullable = false)
    private Dentista dentista;

    @Column(name = "data_ocorrência", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataOcorrência;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sinistro", nullable = false)
    private TipoSinistro tipoSinistro;

    @Lob
    @Column(name = "ação_preventiva_sugerida")
    private String açãoPreventivaSugerida;
}