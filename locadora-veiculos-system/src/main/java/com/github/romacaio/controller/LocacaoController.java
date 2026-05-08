package com.github.romacaio.controller;

import com.github.romacaio.dao.LocacaoDao;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.pagamento.MetodoPagamento;
import com.github.romacaio.model.pagamento.Pagamento;
import com.github.romacaio.model.veiculo.Status;
import com.github.romacaio.model.veiculo.Veiculo;
import com.github.romacaio.service.LocacaoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class LocacaoController {
    private LocacaoDao locacaoDao;
    private List<Locacao> locacoes;
    private LocacaoService locacaoService;
    private VeiculoController veiculoController;

    public LocacaoController(VeiculoController veiculoController) {
        this.locacaoDao = new LocacaoDao();
        this.locacoes = locacaoDao.carregar();
        this.locacaoService = new LocacaoService();
        this.veiculoController = veiculoController;
    }

    public int gerarNovoId() {
        return locacoes.stream()
                .mapToInt(Locacao::getId)
                .max()
                .orElse(0) + 1;
    }

    public void registrarLocacao(Cliente cliente, Veiculo veiculo, int dias) {
        locacaoService.validarVeiculoDisponivel(veiculo);
        locacaoService.validarCliente(cliente);

        if (dias < 0) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero");
        }

        int id = gerarNovoId();
        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataPrevistaDevolucao = dataRetirada.plusDays(dias);
        locacaoService.validarDatasLocacao(dataRetirada, dataPrevistaDevolucao);
        Locacao locacao = new Locacao(id, cliente, veiculo, dataRetirada, dataPrevistaDevolucao);

        veiculoController.atualizarStatusVeiculo(veiculo, Status.LOCADO);
        locacoes.add(locacao);
        locacaoDao.salvar(locacoes);
    }

    public Pagamento devolverVeiculo(int idLocacao, MetodoPagamento metodoPagamento) {
        Locacao locacao = buscarPorId(idLocacao);
        if (locacao == null) {
            throw new IllegalArgumentException("Locacão não econtrada");
        }

        if (locacao.getDataDevolucao() != null) {
            throw new IllegalStateException("Locacão já teve a devolução registrada");
        }

        locacao.registrarDevolucao(LocalDate.now());
        locacaoService.validarDatasDevolucao(locacao.getDataRetirada(), locacao.getDataDevolucao());

        double valorTotal = locacao.calcularValorTotal();
        double valorMulta = locacao.calcularValorMulta();
        Pagamento pagamento = new Pagamento(gerarNovoId(), locacao.getId(), valorMulta, valorTotal, metodoPagamento);
        locacao.setPagamento(pagamento);

        locacaoDao.salvar(locacoes);
        return pagamento;
    }

    public Locacao buscarPorId(int idLocacao) {
        Optional<Locacao> busca = locacoes.stream()
                .filter(loc -> loc.getId() == idLocacao)
                .findFirst();

        return busca.orElse(null);
    }

    public List<Locacao> listarEmAberto() {
        return filtrar(loc -> loc.getDataDevolucao() == null);
    }

    public List<Locacao> listarFinalizadas() {
        return filtrar(loc -> loc.getDataDevolucao() != null);
    }

    private List<Locacao> filtrar(Predicate<Locacao> predicate) {
        return locacoes.stream()
                .filter(predicate)
                .toList();
    }

    public List<Locacao> getLocacoes() {
        return new ArrayList<>(locacoes);
    }
}
