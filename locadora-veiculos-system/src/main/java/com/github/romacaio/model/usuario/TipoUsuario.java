package com.github.romacaio.model.usuario;

public enum TipoUsuario {
    ADMIN("Admin"),
    GERENTE("Gerente"),
    ATENDENTE("Atendente");

    private String namePretty;

    TipoUsuario(String namePretty) {
        this.namePretty = namePretty;
    }

    public static TipoUsuario parse(String tipoUsuario) {
        for (TipoUsuario tipo : values()) {
            if (tipoUsuario.equalsIgnoreCase(tipo.namePretty)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inexistente");
    }

    public String getNamePretty() {
        return namePretty;
    }
}
