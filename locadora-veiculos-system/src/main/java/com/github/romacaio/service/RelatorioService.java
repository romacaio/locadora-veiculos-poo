package com.github.romacaio.service;

import com.github.romacaio.model.locacao.Locacao;
import com.github.romacaio.model.pagamento.Pagamento;
import com.github.romacaio.model.veiculo.Veiculo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioService {
    private DateTimeFormatter formateDateFile = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm");
    private DateTimeFormatter formateDateRelatorio = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public void gerarRelatorioVeiculos(List<Veiculo> veiculos) {
        try {
            Files.createDirectories(Path.of("./relatorios/veiculos"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório (veículos)", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/veiculos/veiculos_" + dataHora + ".pdf"));

            document.open();
            Paragraph titulo = new Paragraph("Relatório Veículos");
            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell("Tipo");
            table.addCell("Modelo");
            table.addCell("Placa");
            table.addCell("Ano");
            table.addCell("Diária");
            table.addCell("Status");

            for (Veiculo veiculo : veiculos) {
                table.addCell(veiculo.getClass().getSimpleName());
                table.addCell(veiculo.getModelo());
                table.addCell(veiculo.getPlaca());
                table.addCell(String.valueOf(veiculo.getAno()));
                table.addCell(String.format("R$ %.2f", veiculo.calcularCustoLocacao(1)));
                table.addCell(veiculo.getStatus().getNamePretty());
            }
            document.add(table);
            document.add(new Paragraph(new Chunk(Chunk.NEWLINE)));
            document.add(new Paragraph("Gerado em: " + dataHora));


        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório (veículos)", e);

        } finally {
            document.close();
        }
    }

    public void gerarRelatorioLocacoes(List<Locacao> locacoes) {
        try {
            Files.createDirectories(Path.of("./relatorios/locacoes"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório (Locações)", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/locacoes/locacoes_" + dataHora + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("Relatório Locacões");
            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Cliente");
            table.addCell("Veículo");
            table.addCell("Retirada");
            table.addCell("Prev.devolução");
            table.addCell("Devolução");
            table.addCell("Valor");

            for (Locacao locacao : locacoes) {
                table.addCell(locacao.getClass().getSimpleName());
                table.addCell(String.valueOf(locacao.getId()));
                table.addCell(locacao.getVeiculo().exibirResumo());
                table.addCell(formateDateRelatorio.format(locacao.getDataRetirada()));
                table.addCell(formateDateRelatorio.format(locacao.getDataPrevistaDevolucao()));
                table.addCell(locacao.getDataDevolucao() == null ? "-" : formateDateRelatorio.format(locacao.getDataPrevistaDevolucao()));
                table.addCell(String.format("R$ %.2f", locacao.getPagamento().getValorTotal()));
            }

            document.add(table);
            document.add(new Paragraph(new Chunk(Chunk.NEWLINE)));
            document.add(new Paragraph("Gerado em: " + dataHora));

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório (locações)", e);

        } finally {
            document.close();
        }
    }

    public void gerarRelatorioFaturamentoMensal(List<Pagamento> pagamentos) {
        try {
            Files.createDirectories(Path.of("./relatorios/faturamento"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório (faturamento)", e);
        }

        String dataHora = formateDateFile.format(LocalDateTime.now());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorios/faturamento/faturamentoMensal_" + dataHora + ".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("Relatório Faturamento Mensal");
            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Mês");
            table.addCell("Faturamento");

            
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar relatório (faturamento)", e);

        } finally {
            document.close();
        }
    }
}
