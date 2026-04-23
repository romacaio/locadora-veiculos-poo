package com.github.romacaio.controller;

import com.github.romacaio.dao.LocacaoDao;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.pagamento.MetodoPagamento;
import com.github.romacaio.model.pagamento.Pagamento;
import com.github.romacaio.model.veiculo.Status;
import com.github.romacaio.model.veiculo.Veiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocacaoController {
    private LocacaoDao locacaoDao;
    private List<Locacao> locacoes;

    public LocacaoController() {
        this.locacaoDao = new LocacaoDao();
        this.locacoes = locacaoDao.carregar();
    }

    public int gerarNovoId() {
        return locacoes.stream()
                .mapToInt(Locacao::getId)
                .max()
                .orElse(0) + 1;
    }

    public void registrarLocacao(Cliente cliente, Veiculo veiculo, int dias) {
        if (veiculo.getStatus() != Status.DISPONIVEL) {
            throw new IllegalStateException("Veículo não está disponível");
        }

        int id = gerarNovoId();
        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataPrevistaDevolucao = dataRetirada.plusDays(dias);
        Locacao locacao = new Locacao(id, cliente, veiculo, dataRetirada, dataPrevistaDevolucao);

        veiculo.setStatus(Status.LOCADO);
        locacoes.add(locacao);
        locacaoDao.salvar(locacoes);
    }

    public double devolverVeiculo(int idLocacao, MetodoPagamento metodoPagamento) {
        Locacao locacao = buscarPorId(idLocacao);
        if (locacao == null) {
            throw new IllegalArgumentException("Locacão não econtrada");
        }

        locacao.registrarDevolucao(LocalDate.now());

        double valor = locacao.calcularValorTotal();
        Pagamento pagamento = new Pagamento(gerarNovoId(), locacao.getId(), valor, metodoPagamento);
        locacao.setPagamento(pagamento);

        locacaoDao.salvar(locacoes);
        return valor;
    }

    public Locacao buscarPorId(int idLocacao) {
        Optional<Locacao> resultado = locacoes.stream()
                .filter(loc -> loc.getId() == idLocacao)
                .findFirst();

        return resultado.orElse(null);
    }

    public List<Locacao> listarEmAberto() {
        return locacoes.stream()
                .filter(l -> l.getDataDevolucao() == null)
                .toList();
    }

    public List<Locacao> getListaLocacoes() {
        return new ArrayList<>(locacoes);
    }
}
