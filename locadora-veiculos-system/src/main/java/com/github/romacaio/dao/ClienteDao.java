package com.github.romacaio.dao;

import com.github.romacaio.model.cliente.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements Persistencia<Cliente> {
    private static final Path ARQUIVO = Path.of("./json/clientes.json");
    private Gson gson;

    public ClienteDao() {
        this.gson = JsonConfig.criarGson();
    }

    @Override
    public void salvar(List<Cliente> dados) {
        try {
            Files.createDirectories(ARQUIVO.getParent());

            String json = gson.toJson(dados);
            Files.writeString(ARQUIVO, json, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar clientes", e);
        }
    }

    @Override
    public List<Cliente> carregar() {
        try {
            String json = Files.readString(ARQUIVO);
            Type tipo = new TypeToken<List<Cliente>>() {
            }.getType();

            List<Cliente> lista = gson.fromJson(json, tipo);
            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
