package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.veiculo.Veiculo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroLocacao extends JFrame {
    private LocacaoController locacaoController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;

    private JTextField campoCPF;
    private JTextField campoPlaca;
    private JSpinner spinnerDias;
    private JList<String> listClientes;
    private DefaultListModel<String> modelClientes;
    private JList<String> listVeiculos;
    private DefaultListModel<String> modelVeiculos;
    private JTextArea areaLocacoes;

    public TelaCadastroLocacao(LocacaoController locacaoController, ClienteController clienteController, VeiculoController veiculoController) {
        this.locacaoController = locacaoController;
        this.clienteController = clienteController;
        this.veiculoController = veiculoController;

        setTitle("Registrar locação");

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(panelFormulario, BorderLayout.WEST);

        this.campoCPF = new JTextField();
        this.campoPlaca = new JTextField();
        this.listClientes = new JList<>();
        this.areaLocacoes = new JTextArea();
        areaLocacoes.setEditable(false);

        this.spinnerDias = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerDias);
        spinnerDias.setEditor(editor);
        editor.getTextField().setEditable(false);

        configurarCampo(campoCPF);
        configurarCampo(campoPlaca);
        configurarCampo(spinnerDias);

        panelFormulario.add(new JLabel("CPF:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoCPF);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Placa:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(campoPlaca);

        panelFormulario.add(Box.createVerticalStrut(15));

        panelFormulario.add(new JLabel("Dias:"));
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(spinnerDias);

        panelFormulario.add(Box.createVerticalStrut(35));

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.setBackground(new Color(0x144202));
        botaoRegistrar.setForeground(Color.white);
        botaoRegistrar.addActionListener(event -> registrarLocacao());
        panelFormulario.add(botaoRegistrar);

        JPanel panelAreas = new JPanel();
        panelAreas.setLayout(new BoxLayout(panelAreas, BoxLayout.Y_AXIS));
        panel.add(panelAreas, BorderLayout.EAST);

        JLabel labelClientes = new JLabel("clientes cadastrados");
        panelAreas.add(labelClientes);

        panelAreas.add(Box.createVerticalStrut(5));

        this.modelClientes = new DefaultListModel<>();
        this.listClientes = new JList<>(modelClientes);
        atualizarListaClientes();
        JScrollPane jscrollPane1 = new JScrollPane(listClientes);

        configurarCampo(jscrollPane1);
        panelAreas.add(jscrollPane1);

        JLabel labelVeiculos = new JLabel("veículos cadastrados");
        panelAreas.add(labelVeiculos);

        panelAreas.add(Box.createVerticalStrut(5));

        this.modelVeiculos = new DefaultListModel<>();
        this.listVeiculos = new JList<>(modelVeiculos);
        atualizarListaVeiculos();
        JScrollPane jscrollPane2 = new JScrollPane(listVeiculos);

        configurarCampo(jscrollPane2);
        panelAreas.add(jscrollPane2);

        JLabel labelLocacoes = new JLabel("locações registradas");
        panelAreas.add(labelLocacoes);

        panelAreas.add(Box.createVerticalStrut(5));

        JScrollPane jScrollPane3 = new JScrollPane(areaLocacoes);
        jScrollPane3.setMaximumSize(new Dimension(200, 250));
        jScrollPane3.setPreferredSize(new Dimension(200, 250));
        jScrollPane3.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAreas.add(jScrollPane3);
        atualizarListaLocacoes();

        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarCampo(JComponent componente) {
        if (componente.getClass() == JScrollPane.class) {
            componente.setMaximumSize(new Dimension(200, 80));
            componente.setPreferredSize(new Dimension(200, 80));
            componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        } else {
            componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            componente.setPreferredSize(new Dimension(120, 30));
            componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }

    public void registrarLocacao() {
        String cpf = campoCPF.getText();
        String placa = campoPlaca.getText();
        int dias = Integer.parseInt(spinnerDias.getValue().toString());

        if (cpf.isEmpty() || placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Cliente cliente = clienteController.buscarPorCpf(cpf);
            Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
            locacaoController.registrarLocacao(cliente, veiculo, dias);
            JOptionPane.showMessageDialog(this, "Locação registrada com sucesso!");
            atualizarListaLocacoes();
            limparCampos();
            atualizarListaVeiculos();

        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarListaLocacoes() {
        StringBuilder sb = new StringBuilder();
        if (locacaoController.getLocacoes().isEmpty()) {
            sb.append("Sem locacações registradas");
            areaLocacoes.setText(sb.toString());

            return;
        }

        locacaoController.getLocacoes()
                .forEach(loc -> sb.append(loc.toString())
                        .append("\n")
                );

        areaLocacoes.setText(sb.toString());
    }

    public void atualizarListaClientes() {
        modelClientes.clear();

        List<String> list = clienteController.getClientes().stream()
                .map(Cliente::exibirResumo)
                .toList();

        if (list.isEmpty()) {
            modelClientes.addElement("Sem clientes cadastrados");
            return;
        }
        modelClientes.addAll(list);
    }

    public void atualizarListaVeiculos() {
        modelVeiculos.clear();
        List<String> list = veiculoController.listarDisponiveis().stream()
                .map(Veiculo::exibirResumo)
                .toList();

        if (list.isEmpty()) {
            modelVeiculos.addElement("Sem veículos disponíveis");
        }
        modelVeiculos.addAll(list);
    }

    public void limparCampos() {
        this.campoCPF.setText("");
        this.campoPlaca.setText("");
        this.spinnerDias.setValue(1);
    }
}
