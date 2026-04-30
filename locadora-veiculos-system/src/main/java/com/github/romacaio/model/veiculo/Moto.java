package com.github.romacaio.model.veiculo;

public class Moto extends Veiculo {

    public Moto(String modelo, String placa, int ano, Status status) {
        super(modelo, placa, ano, status);
    }

    public Moto(String modelo, String placa, int ano) {
        super(modelo, placa, ano);
    }

    @Override
    public double calcularCustoLocacao(int dias) {
        return dias * 40;
    }
}
