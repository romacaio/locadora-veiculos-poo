package com.github.romacaio.controller;

import com.github.romacaio.dao.ClienteDao;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.util.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteController {
    private ClienteDao clienteDao;
    private LocacaoController locacaoController;
    private List<Cliente> clientes;


    public ClienteController(LocacaoController locacaoController) {
        this.clienteDao = new ClienteDao();
        this.locacaoController = locacaoController;
        this.clientes = clienteDao.carregar();
    }

    public void cadastrarCliente(Cliente cliente) {
        if (!ValidatorUtil.isNomeValido(cliente.getNome())) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (!ValidatorUtil.isCpfValido(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if (!ValidatorUtil.isTelefoneValido(cliente.getTelefone())) {
            throw new IllegalArgumentException("Telefone inválido");
        }

        if (!ValidatorUtil.isEmailValido(cliente.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }

        boolean existe = clientes.stream()
                .anyMatch(c -> c.equals(cliente));

        if (existe) {
            throw new IllegalArgumentException("Já existe cliente com esse CPF");
        }

        clientes.add(cliente);
        clienteDao.salvar(clientes);
    }

    public void removerCliente(String cpf) {
        Cliente cliente = buscarPorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        if (hasLocacaoAtiva(cliente)) {
            throw new IllegalStateException("Cliente possui locação ativa e não pode ser removido");
        }

        clientes.remove(cliente);
        clienteDao.salvar(clientes);
    }

    public boolean hasLocacaoAtiva(Cliente cliente) {
        return locacaoController.getLocacoes().stream()
                .anyMatch(loc -> loc.getCliente().equals(cliente)
                        && loc.getDataDevolucao() == null);
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
