package com.github.romacaio.model.pagamento;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private int idLocacao;
    private double valor;
    private LocalDate dataPagamento;
    private MetodoPagamento metodoPagamento;

    public Pagamento(int id, int idLocacao, double valor, MetodoPagamento metodoPagamento) {
        this.id = id;
        this.idLocacao = idLocacao;
        this.valor = valor;
        this.dataPagamento = LocalDate.now();
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

}
