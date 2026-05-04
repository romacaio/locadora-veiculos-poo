package com.github.romacaio.service;

import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.veiculo.Status;
import com.github.romacaio.model.veiculo.Veiculo;

import java.time.LocalDate;

public class LocacaoService {

    public void validarDatasLocacao(LocalDate retirada, LocalDate devolucaoPrevista) {
        if (retirada == null || devolucaoPrevista == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        if (retirada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de retirada não pode ser no passado");
        }

        if (!devolucaoPrevista.isAfter(retirada)) {
            throw new IllegalArgumentException("Data de devolução deve ser após a retirada");
        }
    }

    public void validarDatasDevolucao(LocalDate retirada, LocalDate devolucao) {
        if (retirada == null || devolucao == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        if (devolucao.isBefore(retirada)) {
            throw new IllegalArgumentException("Data de devolução deve ser após a retirada");
        }
    }

    public void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido");
        }
    }

    public void validarVeiculoDisponivel(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo inválido");
        }

        if (veiculo.getStatus() == Status.LOCADO) {
            throw new IllegalStateException("O veículo já está locado");
        }
    }
}
