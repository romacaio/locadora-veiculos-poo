package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.controller.VeiculoController;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private UsuarioController usuarioController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private LocacaoController locacaoController;

    private JTextField campoUserName;
    private JPasswordField campoSenha;

    public TelaLogin(
            UsuarioController usuarioController,
            ClienteController clienteController,
            VeiculoController veiculoController,
            LocacaoController locacaoController

    ) {
        this.usuarioController = usuarioController;
        this.clienteController = clienteController;
        this.veiculoController = veiculoController;
        this.locacaoController = locacaoController;

        setTitle("Login usuário");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.CENTER);

        this.campoUserName = new JTextField();
        this.campoSenha = new JPasswordField();

        configurarCampo(campoUserName);
        configurarCampo(campoSenha);

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelFormulario.add(Box.createVerticalStrut(40));

        panelFormulario.add(labelUsuario);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoUserName);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(labelSenha);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoSenha);

        panelFormulario.add(Box.createVerticalStrut(30));


        JButton botaoLogar = new JButton("Login");
        botaoLogar.setBackground(new Color(0x144202));
        botaoLogar.setForeground(Color.WHITE);
        botaoLogar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoLogar.addActionListener(event -> logarUsuario());
        panelFormulario.add(botaoLogar);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarCampo(JComponent component) {
        component.setMaximumSize(new Dimension(120, 30));
        component.setPreferredSize(new Dimension(120, 30));
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void logarUsuario() {
        String usuario = campoUserName.getText();
        String senha = new String(campoSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuarioController.loginUsuario(usuario, senha);
            JOptionPane.showMessageDialog(this, "Usuário logado com sucesso!");
            new TelaPrincipal(usuarioController, clienteController, veiculoController, locacaoController);
            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        this.campoUserName.setText("");
        this.campoSenha.setText("");
    }
}
