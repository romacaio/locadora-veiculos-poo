package com.github.romacaio.model.veiculo;

public abstract class Veiculo {
    private String modelo;
    private String placa;
    private int ano;
    private Status status;

    public Veiculo(String modelo, String placa, int ano) {
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.status = Status.DISPONIVEL;
    }

    public Veiculo(String modelo, String placa, int ano, Status status) {
        this(modelo, placa, ano);
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Veiculo)) return false;
        Veiculo other = (Veiculo) obj;
        return placa.equalsIgnoreCase(other.getPlaca());
    }

    @Override
    public int hashCode() {
        return placa.hashCode();
    }

    public abstract double calcularCustoLocacao(int dias);

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " {" +
                "\n Modelo: " + modelo + " (" + ano + ")" +
                "\n Placa: " + placa +
                "\n Status: " + status +
                "\n}";

    }

    public String exibirResumo() {
        return modelo + " - " + placa;
    }
}
