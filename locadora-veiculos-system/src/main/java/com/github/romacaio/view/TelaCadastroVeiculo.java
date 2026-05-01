package com.github.romacaio.view;

import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.model.veiculo.*;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroVeiculo extends JFrame {
    private VeiculoController veiculoController;

    private JTextField campoPlaca;
    private JTextField campoModelo;
    private JTextField campoAno;
    private JComboBox<String> comboTipo;
    private JTextArea areaVeiculos;


    public TelaCadastroVeiculo() {
        this.veiculoController = new VeiculoController();

        setTitle("Cadastro veículo");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.WEST);

        this.campoPlaca = new JTextField();
        this.campoModelo = new JTextField();
        this.campoAno = new JTextField();
        this.comboTipo = new JComboBox<>(new String[]{"Carro", "Caminhão", "Moto"});

        this.areaVeiculos = new JTextArea();
        areaVeiculos.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(areaVeiculos);
        atualizarListaVeiculos();

        configurarCampo(campoPlaca);
        configurarCampo(campoModelo);
        configurarCampo(campoAno);
        configurarCampo(comboTipo);

        panelFormulario.add(new JLabel("Placa:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoPlaca);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Modelo:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoModelo);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Ano:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoAno);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(comboTipo);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.add(panelInferior, BorderLayout.SOUTH);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBackground(new Color(0x144202));
        botaoCadastrar.setForeground(Color.WHITE);
        panelInferior.add(botaoCadastrar);

        JButton botaoRemover = new JButton("Remover");
        botaoRemover.setBackground(Color.red);
        botaoRemover.setForeground(Color.WHITE);
        panelInferior.add(botaoRemover);

        botaoCadastrar.addActionListener(event -> cadastrarVeiculo());
        botaoRemover.addActionListener(event -> removerVeiculo());

        JPanel panelAreaVeiculos = new JPanel();
        panelAreaVeiculos.setLayout(new BoxLayout(panelAreaVeiculos, BoxLayout.Y_AXIS));
        panel.add(panelAreaVeiculos, BorderLayout.EAST);

        JLabel labelTitulo = new JLabel("Veículos cadastrados");
        panelAreaVeiculos.add(labelTitulo);

        panelAreaVeiculos.add(Box.createVerticalStrut(5));

        jScrollPane.setMaximumSize(new Dimension(200, 250));
        jScrollPane.setPreferredSize(new Dimension(200, 250));
        jScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAreaVeiculos.add(Box.createVerticalStrut(5));
        panelAreaVeiculos.add(jScrollPane);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarCampo(JComponent componente) {
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        componente.setPreferredSize(new Dimension(120, 30));
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void cadastrarVeiculo() {

        String placa = campoPlaca.getText();
        String modelo = campoModelo.getText();
        String ano = campoAno.getText();
        String tipo = comboTipo.getSelectedItem().toString();

        if (placa.isEmpty() || modelo.isEmpty() || ano.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anoInt;
        try {
            anoInt = Integer.parseInt(ano);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Veiculo veiculo;
        switch (tipo) {
            case ("Carro") -> veiculo = new Carro(modelo, placa, anoInt);
            case ("Moto") -> veiculo = new Moto(modelo, placa, anoInt);
            case ("Caminhão") -> veiculo = new Caminhao(modelo, placa, anoInt);
            default -> {
                JOptionPane.showMessageDialog(this, "Tipo de veículo inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        try {
            veiculoController.cadastrarVeiculo(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
            limparCampos();
            atualizarListaVeiculos();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removerVeiculo() {
        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo para remover:");
        if (placa == null || placa.isEmpty()) return;

        try {
            veiculoController.removerVeiculo(placa);
            JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!");
            atualizarListaVeiculos();

        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarListaVeiculos() {
        StringBuilder sb = new StringBuilder();

        if (veiculoController.getVeiculos().isEmpty()) {
            sb.append("Sem veículos cadastrados");
            areaVeiculos.setText(sb.toString());

            return;
        }

        veiculoController.getVeiculos()
                .forEach(veiculo -> sb.append(veiculo.toString())
                        .append("\n"));

        areaVeiculos.setText(sb.toString());
    }

    public void limparCampos() {
        this.campoPlaca.setText("");
        this.campoModelo.setText("");
        this.campoAno.setText("");
    }
}
