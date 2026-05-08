package com.github.romacaio.service;

import com.github.romacaio.model.cliente.Cliente;
import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.veiculo.Veiculo;
import com.github.romacaio.util.DateUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {
    private DateTimeFormatter formateDateFile = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm");
    private DateTimeFormatter formateDateRelatorio = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public void gerarRelatorioVeiculosLocacoes(List<Locacao> locacoes) {
        try {
            Files.createDirectories(Path.of("./relatorios/veiculos"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/veiculos/veiculos_" + dataHora + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("Relatório locações por veículos");
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);

            document.add(titulo);

            PdfPTable tabela = criarTabela(6, "ID", "Placa", "Cliente", "Data Retirada", "Devolução", "Valor");

            Map<Veiculo, List<Locacao>> locacoesPorVeiculos = locacoes.stream()
                    .collect(Collectors.groupingBy(Locacao::getVeiculo));

            for (Map.Entry<Veiculo, List<Locacao>> entry : locacoesPorVeiculos.entrySet()) {
                if (entry.getValue().isEmpty()) continue;

                String nome = entry.getKey().getModelo();
                Paragraph veiculo = new Paragraph("Veículo: " + nome);
                veiculo.setSpacingAfter(15);
                document.add(veiculo);

                for (Locacao locacao : entry.getValue()) {
                    tabela.addCell(String.valueOf(locacao.getId()));
                    tabela.addCell(locacao.getVeiculo().getPlaca());
                    tabela.addCell(locacao.getCliente().getNome());
                    tabela.addCell(formateDateRelatorio.format(locacao.getDataRetirada()));
                    tabela.addCell(locacao.getDataDevolucao() == null ? "-" : formateDateRelatorio.format(locacao.getDataPrevistaDevolucao()));
                    tabela.addCell(String.format("R$ %.2f", locacao.getPagamento().getValorTotal()));
                }
                tabela.setSpacingAfter(15);
                document.add(tabela);
                tabela = criarTabela(6, "ID", "Placa", "Cliente", "Data Retirada", "Devolução", "Valor");
            }

            document.add(new Paragraph(new Chunk(Chunk.NEWLINE)));
            document.add(new Paragraph("Gerado em: " + dataHora));

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);

        } finally {
            document.close();
        }
    }

    public void gerarRelatorioClientesLocacoes(List<Locacao> locacoes) {
        try {
            Files.createDirectories(Path.of("./relatorios/locacoes"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/locacoes/locacoes_" + dataHora + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("Relatório locações por clientes");
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);

            document.add(titulo);

            PdfPTable tabela = criarTabela(6, "ID", "Veículo", "Retirada", "Prev.devolução", "Devolução", "Valor");

            Map<Cliente, List<Locacao>> locacoesPorClientes = locacoes.stream()
                    .collect(Collectors.groupingBy(Locacao::getCliente));

            for (Map.Entry<Cliente, List<Locacao>> entry : locacoesPorClientes.entrySet()) {
                if (entry.getValue().isEmpty()) continue;

                String nome = entry.getKey().getNome();
                Paragraph cliente = new Paragraph("Cliente: " + nome);
                cliente.setSpacingAfter(15);
                document.add(cliente);

                for (Locacao locacao : entry.getValue()) {
                    tabela.addCell(String.valueOf(locacao.getId()));
                    tabela.addCell(locacao.getVeiculo().exibirResumo());
                    tabela.addCell(formateDateRelatorio.format(locacao.getDataRetirada()));
                    tabela.addCell(formateDateRelatorio.format(locacao.getDataPrevistaDevolucao()));
                    tabela.addCell(locacao.getDataDevolucao() == null ? "-" : formateDateRelatorio.format(locacao.getDataPrevistaDevolucao()));
                    tabela.addCell(String.format("R$ %.2f", locacao.getPagamento().getValorTotal()));
                }
                tabela.setSpacingAfter(15);
                document.add(tabela);
                tabela = criarTabela(6, "ID", "Veículo", "Retirada", "Prev.devolução", "Devolução", "Valor");
            }

            document.add(new Paragraph(new Chunk(Chunk.NEWLINE)));
            document.add(new Paragraph("Gerado em: " + dataHora));

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);

        } finally {
            document.close();
        }
    }

    public void gerarRelatorioFaturamentoMensal(List<Locacao> locacoesFinalizadas) {
        try {
            Files.createDirectories(Path.of("./relatorios/faturamento"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/faturamento/faturamentoMensal_" + dataHora + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("Relatório Faturamento Mensal");
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);

            document.add(titulo);
            PdfPTable tabela = criarTabela(3, "Mês", "Total locações", "Faturamento");

            Map<Month, List<Locacao>> locacoesPorMes = locacoesFinalizadas.stream()
                    .filter(loc -> loc.getPagamento().getDataPagamento().getYear() == Year.now().getValue())
                    .collect(Collectors.groupingBy(loc -> loc.getPagamento().getDataPagamento().getMonth()));

            for (Map.Entry<Month, List<Locacao>> entry : locacoesPorMes.entrySet()) {
                String mes = DateUtil.formatarMes(entry.getKey());
                List<Locacao> listaMes = entry.getValue();
                int totalLocacoes = listaMes.size();

                double faturamento = listaMes.stream()
                        .mapToDouble(loc -> loc.getPagamento().getValorTotal())
                        .sum();

                tabela.addCell(mes);
                tabela.addCell(String.valueOf(totalLocacoes));
                tabela.addCell(String.format("R$ %.2f", faturamento));
            }

            document.add(tabela);
            document.add(new Paragraph(new Chunk(Chunk.NEWLINE)));
            document.add(new Paragraph("Gerado em: " + dataHora));

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);

        } finally {
            document.close();
        }
    }

    private PdfPTable criarTabela(int colunas, String... cabecalhos) {
        PdfPTable tabela = new PdfPTable(colunas);
        tabela.setWidthPercentage(100);

        for (String cabecalho : cabecalhos) {
            tabela.addCell(cabecalho);
        }

        return tabela;
    }
}

