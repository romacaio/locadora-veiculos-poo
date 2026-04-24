package com.github.romacaio.dao;

import com.github.romacaio.model.usuario.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements Persistencia<Usuario> {
    private static final Path ARQUIVO = Path.of("./json/usuarios.json");
    private Gson gson;

    public UsuarioDao() {
        this.gson = JsonConfig.criarGson();
    }

    @Override
    public void salvar(List<Usuario> dados) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            String json = gson.toJson(dados);
            Files.writeString(ARQUIVO, json, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar usuários");
        }
    }

    @Override
    public List<Usuario> carregar() {
        try {
            String json = Files.readString(ARQUIVO);
            Type tipo = new TypeToken<List<Usuario>>() {
            }.getType();

            List<Usuario> lista = gson.fromJson(json, tipo);
            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
