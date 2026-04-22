package com.github.romacaio.model.veiculo;

public class Carro extends Veiculo {

    public Carro(String modelo, String placa, int ano, Status status) {
        super(modelo, placa, ano, status);
    }

    @Override
    public double calcularCustoLocacao(int dias) {
        return dias * 80;
    }
}
