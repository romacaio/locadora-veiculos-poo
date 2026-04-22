package com.github.romacaio.model.veiculo;

public class Moto extends Veiculo {

    public Moto(String modelo, String placa, int ano, Status status) {
        super(modelo, placa, ano, status);
    }

    @Override
    public double calcularCustoLocacao(int dias) {
        return dias * 40;
    }
}
