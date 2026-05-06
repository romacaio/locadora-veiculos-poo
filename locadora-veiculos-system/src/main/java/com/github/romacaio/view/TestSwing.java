package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.controller.VeiculoController;

public class TestSwing {
    public static void main(String[] args) {
        VeiculoController veiculoController = new VeiculoController();
        LocacaoController locacaoController = new LocacaoController(veiculoController);
        ClienteController clienteController = new ClienteController(locacaoController);
        UsuarioController usuarioController = new UsuarioController();

        TelaLogin telaLogin = new TelaLogin(usuarioController, clienteController, veiculoController, locacaoController);
    }
}
