package com.fiap.br.challenger.domain.model.enums;

public enum SystemicDiseases {
    NENHUMA("Nenhuma"),
    DIABETES("Diabetes"),
    CARDIACA("Cardíaca");

    private final String descricao;

    SystemicDiseases(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
