package com.github.romacaio.model.pagamento;

public enum MetodoPagamento {
    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão Crédito"),
    CARTAO_DEBITO("Cartão Débito"),
    PIX("Pix");

    private String namePretty;

    MetodoPagamento(String namePretty) {
        this.namePretty = namePretty;
    }

    public static MetodoPagamento parse(String metodoPagamento) {
        for (MetodoPagamento metodo : MetodoPagamento.values()) {
            if (metodoPagamento.equalsIgnoreCase(metodo.namePretty)) {
                return metodo;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inexistente");
    }

    public String getNamePretty() {
        return namePretty;
    }
}
