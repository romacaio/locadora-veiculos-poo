package com.github.romacaio.view;


import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.model.cliente.Cliente;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCliente extends JFrame {
    private ClienteController clienteController;

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JTextArea areaClientes;

    public TelaCadastroCliente() {
        LocacaoController locacaoController = new LocacaoController();
        this.clienteController = new ClienteController(locacaoController);

        setTitle("Cadastro cliente");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.WEST);

        this.campoNome = new JTextField();
        this.campoCpf = new JTextField();
        this.campoTelefone = new JTextField();
        this.campoEmail = new JTextField();

        this.areaClientes = new JTextArea();
        areaClientes.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(areaClientes);
        atualizarListaClientes();

        configurarCampo(campoNome);
        configurarCampo(campoCpf);
        configurarCampo(campoTelefone);
        configurarCampo(campoEmail);

        panelFormulario.add(new JLabel("Nome:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoNome);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("CPF:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoCpf);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Telefone:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoTelefone);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoEmail);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.add(panelInferior, BorderLayout.SOUTH);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBackground(new Color(0x144202));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.addActionListener(event -> cadastrarCliente());
        panelInferior.add(botaoCadastrar);

        JButton botaoRemover = new JButton("Remover");
        botaoRemover.setBackground(Color.red);
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.addActionListener(event -> removerCliente());
        panelInferior.add(botaoRemover);

        JPanel panelAreaClientes = new JPanel();
        panelAreaClientes.setLayout(new BoxLayout(panelAreaClientes, BoxLayout.Y_AXIS));
        panel.add(panelAreaClientes, BorderLayout.EAST);

        JLabel labelTitulo = new JLabel("Clientes cadastrados");
        panelAreaClientes.add(labelTitulo);

        panelAreaClientes.add(Box.createVerticalStrut(5));

        jScrollPane.setMaximumSize(new Dimension(200, 250));
        jScrollPane.setPreferredSize(new Dimension(200, 250));
        jScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAreaClientes.add(Box.createVerticalStrut(5));
        panelAreaClientes.add(jScrollPane);


        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void configurarCampo(JComponent component) {
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        component.setPreferredSize(new Dimension(120, 30));
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void cadastrarCliente() {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String telefone = campoTelefone.getText();
        String email = campoEmail.getText();

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Cliente cliente = new Cliente(nome, cpf, telefone, email);
            clienteController.cadastrarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            atualizarListaClientes();
            limparCampos();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removerCliente() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do cliente para remover:");
        if (cpf == null || cpf.isBlank()) return;

        try {
            clienteController.removerCliente(cpf);
            JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
            atualizarListaClientes();

        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarListaClientes() {
        StringBuilder sb = new StringBuilder();

        if (clienteController.getClientes().isEmpty()) {
            sb.append("Sem clientes cadastrados");
            areaClientes.setText(sb.toString());

            return;
        }

        clienteController.getClientes()
                .forEach(cliente -> sb.append(cliente.toString())
                        .append("\n"));

        areaClientes.setText(sb.toString());
    }

    public void limparCampos() {
        this.campoNome.setText("");
        this.campoCpf.setText("");
        this.campoTelefone.setText("");
        this.campoEmail.setText("");
    }
}
