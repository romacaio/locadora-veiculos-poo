package com.github.romacaio.view;

import com.github.romacaio.dao.VeiculoDao;
import com.github.romacaio.model.veiculo.*;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        VeiculoDao veiculoDao = new VeiculoDao();

        List<Veiculo> veiculos = new ArrayList<>(List.of(
                new Carro("Gol", "ABC123", 2020, Status.DISPONIVEL),
                new Moto("CG", "XYZ789", 2022, Status.DISPONIVEL),
                new Caminhao("Volvo FH 540", "ABC123", 2019, Status.DISPONIVEL)
        ));

        veiculoDao.salvar(veiculos);
        List<Veiculo> carregados = veiculoDao.carregar();

        carregados.forEach(System.out::println);

    }
}

