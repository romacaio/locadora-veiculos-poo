package com.github.romacaio.model.veiculo;

public class Caminhao extends Veiculo {
    public Caminhao(String modelo, String placa, int ano, Status status) {
        super(modelo, placa, ano, status);
    }

    public Caminhao(String modelo, String placa, int ano) {
        super(modelo, placa, ano);
    }

    @Override
    public double calcularCustoLocacao(int dias) {
        return dias * 160;
    }
}
