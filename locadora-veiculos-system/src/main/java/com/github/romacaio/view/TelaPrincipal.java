package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.model.usuario.TipoUsuario;
import com.github.romacaio.model.usuario.Usuario;
import com.github.romacaio.service.PermissaoService;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private UsuarioController usuarioController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private LocacaoController locacaoController;

    private Usuario usuarioLogado;

    public TelaPrincipal(UsuarioController usuarioController, ClienteController clienteController, VeiculoController veiculoController, LocacaoController locacaoController) {
        this.usuarioController = usuarioController;
        this.usuarioLogado = usuarioController.getUsuarioLogado();
        this.clienteController = clienteController;
        this.veiculoController = veiculoController;
        this.locacaoController = locacaoController;

        setTitle("Locadora");
        setLayout(new BorderLayout());

        JLabel bemVindoLabel = new JLabel("Bem-vindo, " + usuarioLogado.getUserName());
        bemVindoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bemVindoLabel.setFont(new Font("Bebas Neue", Font.BOLD, 15));

        add(bemVindoLabel, BorderLayout.NORTH);

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panelMenu, BorderLayout.WEST);

        JButton botaoClientes = new JButton("Clientes");
        botaoClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configurarCampo(botaoClientes);
        botaoClientes.addActionListener(event -> abrirTelaClientes());
        panelMenu.add(botaoClientes);

        panelMenu.add(Box.createVerticalStrut(20));

        JButton botaoVeiculos = new JButton("Veículos");
        botaoVeiculos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configurarCampo(botaoVeiculos);
        botaoVeiculos.addActionListener(event -> abrirTelaVeiculos());
        panelMenu.add(botaoVeiculos);

        panelMenu.add(Box.createVerticalStrut(35));

        JButton botaoLocacoes = new JButton("Locações");
        botaoLocacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configurarCampo(botaoLocacoes);
        botaoLocacoes.addActionListener(event -> abrirTelaLocacoes());
        panelMenu.add(botaoLocacoes);

        panelMenu.add(Box.createVerticalStrut(20));

        JButton botaoUsuarios = new JButton("Usuários");
        botaoUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configurarCampo(botaoUsuarios);
        panelMenu.add(botaoUsuarios);

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/carros.png"));

        int larguraOriginal = icon.getIconWidth();
        int alturaOriginal = icon.getIconHeight();

        int novaLargura = 700;
        int novaAltura = (alturaOriginal * novaLargura) / larguraOriginal;

        Image imagemRedimensionada = icon.getImage().getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);

        JLabel labelImagem = new JLabel(new ImageIcon(imagemRedimensionada));

        JPanel panelImagem = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        panelImagem.add(labelImagem);
        add(panelImagem, BorderLayout.CENTER);

        setSize(900, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarCampo(JComponent component) {
        component.setMaximumSize(new Dimension(120, 40));
        component.setPreferredSize(new Dimension(120, 40));
        component.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    private void abrirTelaClientes() {
        try {
            PermissaoService.verificar(usuarioLogado, TipoUsuario.ADMIN, TipoUsuario.GERENTE);
            new TelaCadastroCliente(clienteController);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acesso negado", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void abrirTelaVeiculos() {
        try {
            PermissaoService.verificar(usuarioLogado, TipoUsuario.ADMIN, TipoUsuario.GERENTE);
            new TelaCadastroVeiculo(veiculoController);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acesso negado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaLocacoes() {
        try {
            PermissaoService.verificar(usuarioLogado, TipoUsuario.ADMIN, TipoUsuario.GERENTE, TipoUsuario.ATENDENTE);
            new TelaCadastroLocacao(locacaoController, clienteController, veiculoController);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Acesso negado", JOptionPane.ERROR_MESSAGE);
        }
    }
}
