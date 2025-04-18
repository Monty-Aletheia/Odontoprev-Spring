package com.fiap.br.challenger.domain.model.enums;

import lombok.Getter;

@Getter
public enum SystemicDiseases {
    NENHUMA("Nenhuma"),
    DIABETES("Diabetes"),
    CARDIACA("Cardíaca");

    private final String descricao;

    SystemicDiseases(String descricao) {
        this.descricao = descricao;
    }

}
