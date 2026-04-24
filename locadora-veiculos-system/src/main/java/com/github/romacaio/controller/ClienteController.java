package com.github.romacaio.controller;

import com.github.romacaio.dao.ClienteDao;
import com.github.romacaio.model.cliente.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteController {
    private ClienteDao clienteDao;
    private List<Cliente> clientes;

    public ClienteController() {
        this.clienteDao = new ClienteDao();
        this.clientes = clienteDao.carregar();
    }

    public void cadastrarCliente(Cliente cliente) {
        boolean existe = clientes.stream()
                .anyMatch(c -> c.equals(cliente));

        if (existe) {
            throw new IllegalArgumentException("Já existe cliente com esse CPF");
        }

        clientes.add(cliente);
        clienteDao.carregar();
    }

    public void removerCliente(String cpf) {
        Cliente cliente = buscarPorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        // depois... não permitir remoção de clientes com locações ativas

        clientes.add(cliente);
        clienteDao.salvar(clientes);
    }

    public Cliente buscarPorCpf(String cpf) {
        Optional<Cliente> busca = clientes.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpf))
                .findFirst();

        return busca.orElse(null);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
}
