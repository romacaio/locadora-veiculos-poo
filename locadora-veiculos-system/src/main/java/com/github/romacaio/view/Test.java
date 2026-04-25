package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.dao.ClienteDao;
import com.github.romacaio.dao.LocacaoDao;
import com.github.romacaio.dao.UsuarioDao;
import com.github.romacaio.dao.VeiculoDao;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.usuario.TipoUsuario;
import com.github.romacaio.model.usuario.Usuario;
import com.github.romacaio.model.veiculo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        VeiculoDao veiculoDao = new VeiculoDao();
        ClienteDao clienteDao = new ClienteDao();
        LocacaoDao locacaoDao = new LocacaoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        LocacaoController locacaoController = new LocacaoController();
        VeiculoController veiculoController = new VeiculoController();
        ClienteController clienteController = new ClienteController(locacaoController);
        UsuarioController usuarioController = new UsuarioController();

        List<Veiculo> veiculos = new ArrayList<>(List.of(
                new Carro("Gol", "ABC123", 2020, Status.DISPONIVEL),
                new Moto("CG", "XYZ789", 2022, Status.DISPONIVEL),
                new Caminhao("Volvo FH 540", "ABC123", 2019, Status.DISPONIVEL)
        ));

        List<Cliente> clientes = new ArrayList<>(List.of(
                new Cliente("Germán Ezequiel Cano", "016.332.414-28", "8399914779", "caio12@gmail.com"),
                new Cliente("Frederico Chaves Guedes", "085.168.714-30", "219124589", "caio12@gmail.com")

        ));

        // VeiculoDao
        veiculoDao.salvar(veiculos);
        List<Veiculo> veiculosLoaded = veiculoDao.carregar();
        veiculosLoaded.forEach(System.out::println);

        System.out.println("-".repeat(30));

        // ClienteDao
        clienteDao.salvar(clientes);
        List<Cliente> clientesLoaded = clienteDao.carregar();
        clientesLoaded.forEach(System.out::println);

        System.out.println("-".repeat(30));

        // LocacaoDao
        List<Locacao> locacoes = List.of(
                new Locacao(1, clientesLoaded.get(0), veiculosLoaded.get(0),
                        LocalDate.now(), LocalDate.now().plusDays(3))
        );

        locacaoDao.salvar(locacoes);
        List<Locacao> locLoaded = locacaoDao.carregar();
        locLoaded.forEach(System.out::println);

        System.out.println("-".repeat(30));

        // UsuarioDao
        List<Usuario> usuarios = List.of(
                new Usuario("Maria", "142", TipoUsuario.ADMIN),
                new Usuario("joao", "123", TipoUsuario.ATENDENTE)
        );

        usuarioDao.salvar(usuarios);
        usuarioDao.carregar().forEach(System.out::println);

        System.out.println("-".repeat(30));

        // LocacaoController
        locacaoController.registrarLocacao(clientes.get(1), veiculos.get(1), 3);
        locacaoController.getLocacoes().forEach(System.out::println);

        System.out.println("-".repeat(30));

        // VeiculoController
        veiculoController.cadastrarVeiculo(new Carro("Gol", "ABC123", 2020, Status.DISPONIVEL));
        veiculoController.listarLocados().forEach(System.out::println);
        veiculoController.listarDisponiveis().forEach(System.out::println);

        System.out.println("-".repeat(30));

        // ClienteController
        clienteController.cadastrarCliente(new Cliente("João", "123", "9999", "email@email.com"));
        clienteController.getClientes().forEach(System.out::println);

        System.out.println("-".repeat(30));

        // UsuarioController
        usuarioController.cadastrarUsuario(new Usuario("Luana", "345", TipoUsuario.ADMIN));
        usuarioController.loginUsuario("Luana", "345");
        System.out.println(usuarioController.getUsuarioLogado());
    }
}

