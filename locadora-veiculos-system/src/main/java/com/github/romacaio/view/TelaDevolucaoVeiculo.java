package com.github.romacaio.view;

import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.pagamento.MetodoPagamento;
import com.github.romacaio.model.pagamento.Pagamento;

import javax.swing.*;
import java.awt.*;

public class TelaDevolucaoVeiculo extends JFrame {
    private LocacaoController locacaoController;

    private JTextField campoIdLocacao;
    private JComboBox<String> comboBoxPagamento;
    private JTextArea areaLocacoesAtivas;

    public TelaDevolucaoVeiculo(LocacaoController locacaoController) {
        this.locacaoController = locacaoController;
        setTitle("Devolução veículo");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.WEST);

        this.campoIdLocacao = new JTextField();
        this.comboBoxPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão Crédito", "Cartão Débito", "Pix"});

        this.areaLocacoesAtivas = new JTextArea();
        areaLocacoesAtivas.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(areaLocacoesAtivas);
        atualizarListaLocacoes();

        configurarCampo(campoIdLocacao);
        configurarCampo(comboBoxPagamento);

        panelFormulario.add(new JLabel("ID locação:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoIdLocacao);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Método pagamento:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(comboBoxPagamento);

        panelFormulario.add(Box.createVerticalStrut(35));

        JButton botaoRegistrar = new JButton("Devolução");
        botaoRegistrar.setBackground(new Color(0x144202));
        botaoRegistrar.setForeground(Color.white);
        botaoRegistrar.addActionListener(event -> registrarDevolucao());
        panelFormulario.add(botaoRegistrar);

        JPanel panelAreaLocacoes = new JPanel();
        panelAreaLocacoes.setLayout(new BoxLayout(panelAreaLocacoes, BoxLayout.Y_AXIS));
        panel.add(panelAreaLocacoes, BorderLayout.EAST);

        panelAreaLocacoes.add(Box.createVerticalStrut(5));
        JLabel labelTitulo = new JLabel("Locações (em aberto)");
        panelAreaLocacoes.add(labelTitulo);

        panelAreaLocacoes.add(Box.createVerticalStrut(5));
        jScrollPane.setMaximumSize(new Dimension(200, 250));
        jScrollPane.setPreferredSize(new Dimension(200, 250));
        jScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAreaLocacoes.add(Box.createVerticalStrut(5));
        panelAreaLocacoes.add(jScrollPane);

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

    public void registrarDevolucao() {
        String idLocacao = campoIdLocacao.getText();
        MetodoPagamento metodoPagamento = MetodoPagamento.parse(comboBoxPagamento.getSelectedItem().toString());

        int id;
        try {
            id = Integer.parseInt(idLocacao);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "id inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Pagamento pagamento = locacaoController.devolverVeiculo(id, metodoPagamento);
            Locacao locacao = locacaoController.buscarPorId(id);
            JOptionPane.showMessageDialog(this, "devolução registrada com sucesso!\n" +
                    "Dias utilizados: R$ " + locacao.getDias() +
                    "\nMulta: R$ " + pagamento.getValorMulta() +
                    "\nTotal: R$ " + pagamento.getValorTotal());

        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarListaLocacoes() {
        StringBuilder sb = new StringBuilder();
        if (locacaoController.listarEmAberto().isEmpty()) {
            sb.append("Sem locacações registradas");
            areaLocacoesAtivas.setText(sb.toString());

            return;
        }

        locacaoController.getLocacoes()
                .forEach(loc -> sb.append(loc.toString())
                        .append("\n")
                );

        areaLocacoesAtivas.setText(sb.toString());
    }

    public void limparCampos() {
        this.campoIdLocacao.setText("");
    }
}
