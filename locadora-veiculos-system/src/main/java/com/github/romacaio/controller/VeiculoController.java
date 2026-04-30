package com.github.romacaio.controller;

import com.github.romacaio.dao.VeiculoDao;
import com.github.romacaio.model.veiculo.Status;
import com.github.romacaio.model.veiculo.Veiculo;
import com.github.romacaio.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class VeiculoController {
    private VeiculoDao veiculoDao;
    private List<Veiculo> veiculos;

    public VeiculoController() {
        this.veiculoDao = new VeiculoDao();
        this.veiculos = veiculoDao.carregar();
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        if (!Validator.isPlacaValida(veiculo.getPlaca())) {
            throw new IllegalArgumentException("Placa inválida");
        }

        if (!Validator.isAnoVeiculoValido(veiculo.getAno())) {
            throw new IllegalArgumentException("Ano inválido");
        }

        boolean existe = veiculos.stream()
                .anyMatch(v -> v.equals(veiculo));

        if (existe) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com essa placa");
        }

        veiculos.add(veiculo);
        veiculoDao.salvar(veiculos);
    }

    public void removerVeiculo(String placa) {
        Veiculo veiculo = buscarPorPlaca(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não econtrado");
        }

        if (veiculo.getStatus() == Status.LOCADO) {
            throw new IllegalStateException("Não é possívei remover o veículo locado");
        }

        veiculos.remove(veiculo);
        veiculoDao.salvar(veiculos);
    }


    public Veiculo buscarPorPlaca(String placa) {
        Optional<Veiculo> busca = veiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();

        return busca.orElse(null);
    }

    public List<Veiculo> listarDisponiveis() {
        return filtrar(v -> v.getStatus() == (Status.DISPONIVEL));
    }

    public List<Veiculo> listarLocados() {
        return filtrar(v -> v.getStatus() == (Status.LOCADO));
    }

    private List<Veiculo> filtrar(Predicate<Veiculo> predicate) {
        return veiculos.stream()
                .filter(predicate)
                .toList();
    }

    public List<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos);
    }
}
