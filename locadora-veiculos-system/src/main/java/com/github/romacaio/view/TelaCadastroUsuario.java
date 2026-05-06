package com.github.romacaio.view;

import com.github.romacaio.controller.UsuarioController;
import com.github.romacaio.model.usuario.TipoUsuario;
import com.github.romacaio.model.usuario.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JDialog {
    private UsuarioController usuarioController;

    private JTextField campoUserName;
    private JTextField campoSenha;
    private JComboBox<String> comboTipo;

    public TelaCadastroUsuario(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        setTitle("Cadastro usuário");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panel.add(panelFormulario, BorderLayout.CENTER);

        this.campoUserName = new JTextField();
        this.campoSenha = new JTextField();
        this.comboTipo = new JComboBox<>(new String[]{"Admin", "Gerente", "Atendente"});

        configurarCampos(campoUserName);
        configurarCampos(campoSenha);
        configurarCampos(comboTipo);

        JLabel labelUsuario = new JLabel("Username:");
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelTipo = new JLabel("Tipo: ");
        labelTipo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelFormulario.add(Box.createVerticalStrut(25));

        panelFormulario.add(labelUsuario);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoUserName);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(labelSenha);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoSenha);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(labelTipo);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(comboTipo);

        panelFormulario.add(Box.createVerticalStrut(25));

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelFormulario.add(panelBotoes);

        JButton botadoCadastrar = new JButton("Cadastrar");
        botadoCadastrar.setBackground(new Color(0x144202));
        botadoCadastrar.setForeground(Color.WHITE);
        botadoCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botadoCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botadoCadastrar.addActionListener(event -> cadastrarUsuario());
        panelBotoes.add(botadoCadastrar);

        JButton botaoRemover = new JButton("Remover");
        botaoRemover.setBackground(Color.red);
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoRemover.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoRemover.addActionListener(event -> removerUsuario());
        panelBotoes.add(botaoRemover);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void configurarCampos(JComponent component) {
        component.setMaximumSize(new Dimension(120, 30));
        component.setPreferredSize(new Dimension(120, 30));
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void cadastrarUsuario() {
        String userName = campoUserName.getText();
        String senha = campoSenha.getText();
        String tipo = comboTipo.getSelectedItem().toString();

        if (userName.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            TipoUsuario tipoUsuario = TipoUsuario.parse(tipo);
            Usuario usuario = new Usuario(userName, senha, tipoUsuario);
            usuarioController.cadastrarUsuario(usuario);
            limparCampos();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerUsuario() {
        String username = JOptionPane.showInputDialog(this, "Digite o username do usuário para remover:");
        if (username == null || username.isEmpty()) return;

        try {
            usuarioController.removerUsuario(username);
            JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        this.campoUserName.setText("");
        this.campoSenha.setText("");
    }
}
