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
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 40));
        add(BorderLayout.CENTER, panelFormulario);

        this.campoNome = new JTextField();
        this.campoCpf = new JTextField();
        this.campoTelefone = new JTextField();
        this.campoEmail = new JTextField();

        this.areaClientes = new JTextArea();
        areaClientes.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(areaClientes);
        jScrollPane.setPreferredSize(new Dimension(200, 100));
        atualizarListaClientes();

        panelFormulario.add(new JLabel("Nome:"));
        panelFormulario.add(campoNome);

        panelFormulario.add(new JLabel("CPF:"));
        panelFormulario.add(campoCpf);

        panelFormulario.add(new JLabel("Telefone:"));
        panelFormulario.add(campoTelefone);

        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(campoEmail);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        add(BorderLayout.SOUTH, panelInferior);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBackground(new Color(0x144202));
        botaoCadastrar.setForeground(Color.WHITE);
        panelInferior.add(botaoCadastrar);

        JButton botaoRemover = new JButton("Remover");
        botaoRemover.setBackground(Color.red);
        botaoRemover.setForeground(Color.WHITE);
        panelInferior.add(botaoRemover);

        botaoCadastrar.addActionListener(event -> cadastrarCliente());
        botaoRemover.addActionListener(event -> removerCliente());

        panelInferior.add(jScrollPane);

        setSize(480, 320);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
                .forEach(cliente -> sb.append(cliente.getNome())
                        .append(": ")
                        .append(cliente.getCpf())
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
