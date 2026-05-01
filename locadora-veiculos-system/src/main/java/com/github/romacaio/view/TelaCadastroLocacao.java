package com.github.romacaio.view;

import com.github.romacaio.controller.ClienteController;
import com.github.romacaio.controller.LocacaoController;
import com.github.romacaio.controller.VeiculoController;
import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.veiculo.Veiculo;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
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
    private JList<String> listVeiculos;
    private JList<String> listLocacoes;

    public TelaCadastroLocacao() {
        this.locacaoController = new LocacaoController();
        this.clienteController = new ClienteController(locacaoController);
        this.veiculoController = new VeiculoController();

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

        DefaultListModel<String> modelClientes = new DefaultListModel<>();
        this.listClientes = new JList<>(modelClientes);
        modelClientes.addAll(atualizarListaClientes());
        JScrollPane jscrollPane1 = new JScrollPane(listClientes);

        configurarCampo(jscrollPane1);
        panelAreas.add(jscrollPane1);

        JLabel labelVeiculos = new JLabel("veículos cadastrados");
        panelAreas.add(labelVeiculos);

        panelAreas.add(Box.createVerticalStrut(5));

        DefaultListModel<String> modelVeiculos = new DefaultListModel<>();
        this.listVeiculos = new JList<>(modelVeiculos);
        modelVeiculos.addAll(atualizarListaVeiculos());
        JScrollPane jscrollPane2 = new JScrollPane(listVeiculos);

        configurarCampo(jscrollPane2);
        panelAreas.add(jscrollPane2);

        setSize(400, 400);
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

    }

    public List<String> atualizarListaClientes() {
        return clienteController.getClientes().stream()
                .map(Cliente::exibirResumo)
                .toList();
    }

    public List<String> atualizarListaVeiculos() {
        return veiculoController.getVeiculos().stream()
                .map(Veiculo::exibirResumo)
                .toList();
    }
}
