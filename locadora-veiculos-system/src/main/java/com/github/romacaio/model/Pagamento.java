package com.github.romacaio.model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private int idLocacao;
    private double valor;
    private LocalDate dataPagemento;
    private MetodoPagamento metodoPagamento;

    public Pagamento(int id, int idLocacao, double valor, MetodoPagamento metodoPagamento) {
        this.id = id;
        this.idLocacao = idLocacao;
        this.valor = valor;
        this.dataPagemento = LocalDate.now();
        this.metodoPagamento = metodoPagamento;
    }

    public int getId() {
        return id;
    }

    public int getIdLocacao() {
        return idLocacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagemento() {
        return dataPagemento;
    }

    public void setDataPagemento(LocalDate dataPagemento) {
        this.dataPagemento = dataPagemento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

}
