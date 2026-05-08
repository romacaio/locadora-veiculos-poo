package com.github.romacaio.view;

import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.service.RelatorioService;

import javax.swing.*;
import java.awt.*;

public class TelaRelatorios extends JDialog {
    private LocacaoController locacaoController;
    private RelatorioService relatorioService;

    private JComboBox<String> comboBoxRelatorios;

    public TelaRelatorios(LocacaoController locacaoController, RelatorioService relatorioService) {
        this.locacaoController = locacaoController;
        this.relatorioService = relatorioService;

        setTitle("Gerar relatórios");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.CENTER);

        this.comboBoxRelatorios = new JComboBox<>(new String[]{"Faturamento mensal", "Clientes", "Veículos"});
        comboBoxRelatorios.setMaximumSize(new Dimension(120, 30));
        comboBoxRelatorios.setPreferredSize(new Dimension(120, 30));
        comboBoxRelatorios.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelRelatorios = new JLabel("Relatórios disponíveis:");
        labelRelatorios.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelFormulario.add(Box.createVerticalStrut(40));

        panelFormulario.add(labelRelatorios);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(comboBoxRelatorios);

        panelFormulario.add(Box.createVerticalStrut(20));

        JButton botaoGerarRelatorio = configBotao("Gerar");
        botaoGerarRelatorio.setBackground(new Color(0x144202));
        botaoGerarRelatorio.setForeground(Color.WHITE);
        botaoGerarRelatorio.addActionListener(event -> gerarRelatorio());
        panelFormulario.add(botaoGerarRelatorio);

        setSize(400, 260);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private JButton configBotao(String text) {
        JButton botao = new JButton(text);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private void gerarRelatorio() {
        String relatorio = this.comboBoxRelatorios.getSelectedItem().toString();

        switch (relatorio) {
            case ("Faturamento mensal") -> gerarRelatorioFaturamento();
            case ("Clientes") -> gerarRelatorioClientes();
            case ("Veículos") -> gerarRelatorioVeiculos();
        }
        JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!");
    }

    private void gerarRelatorioClientes() {
        try {
            relatorioService.gerarRelatorioClientesLocacoes(locacaoController.getLocacoes());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioVeiculos() {
        try {
            relatorioService.gerarRelatorioVeiculosLocacoes(locacaoController.getLocacoes());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioFaturamento() {
        try {
            relatorioService.gerarRelatorioFaturamentoMensal(locacaoController.listarFinalizadas());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
