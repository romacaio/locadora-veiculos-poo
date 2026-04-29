package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;

public class TestSwing {
    public static void main(String[] args) {
        LocacaoController locacaoController = new LocacaoController();
        new ClienteController(locacaoController);

        TelaCadastroCliente telaCadastroCliente = new TelaCadastroCliente();
    }
}
