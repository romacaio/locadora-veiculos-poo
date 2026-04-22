package com.github.romacaio.view;

import com.github.romacaio.dao.ClienteDao;
import com.github.romacaio.dao.VeiculoDao;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.veiculo.*;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        VeiculoDao veiculoDao = new VeiculoDao();
        ClienteDao clienteDao = new ClienteDao();

        List<Veiculo> veiculos = new ArrayList<>(List.of(
                new Carro("Gol", "ABC123", 2020, Status.DISPONIVEL),
                new Moto("CG", "XYZ789", 2022, Status.DISPONIVEL),
                new Caminhao("Volvo FH 540", "ABC123", 2019, Status.DISPONIVEL)
        ));

        List<Cliente> clientes = new ArrayList<>(List.of(
                new Cliente("Germán Ezequiel Cano", "016.332.414-28", "8399914779", "caio12@gmail.com"),
                new Cliente("Frederico Chaves Guedes", "085.168.714-30", "219124589", "caio12@gmail.com")

        ));

        veiculoDao.salvar(veiculos);
        List<Veiculo> carregados1 = veiculoDao.carregar();
        carregados1.forEach(System.out::println);

        System.out.println("-".repeat(30));

        clienteDao.salvar(clientes);
        List<Cliente> carregados2 = clienteDao.carregar();

        carregados2.forEach(System.out::println);


    }
}

