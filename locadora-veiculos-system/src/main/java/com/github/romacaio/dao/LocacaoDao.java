package com.github.romacaio.dao;

import com.github.romacaio.model.locacao.Locacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDao implements Persistencia<Locacao> {
    private static final Path ARQUIVO = Path.of("./json/locacoes.json");
    private Gson gson;

    public LocacaoDao() {
        this.gson = JsonConfig.criarGson();
    }

    @Override
    public void salvar(List<Locacao> dados) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            Type tipo = new TypeToken<List<Locacao>>() {
            }.getType();

            String json = gson.toJson(dados, tipo);
            Files.writeString(ARQUIVO, json, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar locações", e);
        }
    }

    @Override
    public List<Locacao> carregar() {
        try {
            String json = Files.readString(ARQUIVO);
            Type tipo = new TypeToken<List<Locacao>>() {
            }.getType();

            List<Locacao> lista = gson.fromJson(json, tipo);
            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
