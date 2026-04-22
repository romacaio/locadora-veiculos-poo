package com.github.romacaio.dao;

import com.github.romacaio.model.veiculo.Veiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDao implements Persistencia<Veiculo> {
    private static final Path ARQUIVO = Path.of("./json/veiculos.json");
    private Gson gson;

    public VeiculoDao() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Veiculo.class, new VeiculoAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void salvar(List<Veiculo> dados) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            Type tipo = new TypeToken<List<Veiculo>>() {
            }.getType();

            String json = gson.toJson(dados, tipo);
            Files.writeString(ARQUIVO, json, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar veículos", e);
        }
    }

    @Override
    public List<Veiculo> carregar() {
        try {
            String json = Files.readString(ARQUIVO);
            Type tipo = new TypeToken<List<Veiculo>>() {
            }.getType();

            List<Veiculo> lista = gson.fromJson(json, tipo);
            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
