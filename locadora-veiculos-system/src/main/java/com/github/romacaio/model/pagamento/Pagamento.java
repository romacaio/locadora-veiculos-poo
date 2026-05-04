package com.github.romacaio.model.pagamento;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private int idLocacao;
    private double valorMulta;
    private double valorTotal;
    private LocalDate dataPagamento;
    private MetodoPagamento metodoPagamento;

    public Pagamento(int id, int idLocacao, double valorMulta, double valorTotal, MetodoPagamento metodoPagamento) {
        this.id = id;
        this.idLocacao = idLocacao;
        this.valorMulta = valorMulta;
        this.valorTotal = valorTotal;
        this.dataPagamento = LocalDate.now();
        this.metodoPagamento = metodoPagamento;
    }

    public int getId() {
        return id;
    }

    public int getIdLocacao() {
        return idLocacao;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
}
