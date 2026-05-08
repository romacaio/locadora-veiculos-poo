package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.service.RelatorioService;

public class TestSwing {
    public static void main(String[] args) {
        VeiculoController veiculoController = new VeiculoController();
        LocacaoController locacaoController = new LocacaoController(veiculoController);
        ClienteController clienteController = new ClienteController(locacaoController);
        UsuarioController usuarioController = new UsuarioController();
        RelatorioService relatorioService = new RelatorioService();

        TelaLogin telaLogin = new TelaLogin(usuarioController, clienteController, veiculoController, locacaoController);
        //TelaRelatorios telaRelatorios = new TelaRelatorios(locacaoController, relatorioService);
    }
}
