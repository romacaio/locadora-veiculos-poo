package com.github.romacaio.model.veiculo;

public enum Status {
    DISPONIVEL("Disponível"),
    LOCADO("Locado");

    private String namePretty;

    Status(String namePretty) {
        this.namePretty = namePretty;
    }

    public String getNamePretty() {
        return namePretty;
    }
}
