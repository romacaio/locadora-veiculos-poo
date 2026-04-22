package com.github.romacaio.model.locacao;

import com.github.romacaio.model.pagamento.Pagamento;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.veiculo.Status;
import com.github.romacaio.model.veiculo.Veiculo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Locacao {
    private static final double PORCENTUAL_MULTA_ATRASO = 0.20;

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataRetirada;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private Pagamento pagamento;

    public Locacao(int id, Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, LocalDate dataPrevistaDevolucao) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRetirada = dataRetirada;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public double calcularValorTotal() {
        if (dataDevolucao == null) return 0;
        double valorBase = veiculo.calcularCustoLocacao((int) getDias());
        double multa = 0;

        if (isAtrasado()) {
            long diasAtraso = dataPrevistaDevolucao.until(dataDevolucao, ChronoUnit.DAYS);
            multa = diasAtraso * (veiculo.calcularCustoLocacao(1) * PORCENTUAL_MULTA_ATRASO);
        }
        return valorBase + multa;
    }

    public void registrarDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        this.veiculo.setStatus(Status.DISPONIVEL);
    }

    public boolean isAtrasado() {
        if (dataDevolucao == null) {
            return LocalDate.now().isAfter(dataPrevistaDevolucao);
        }
        return dataDevolucao.isAfter(dataPrevistaDevolucao);
    }

    private long getDias() {
        long dias = dataRetirada.until(dataDevolucao, ChronoUnit.DAYS);
        return dias == 0 ? 1 : dias;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
