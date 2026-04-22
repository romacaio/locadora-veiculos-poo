package com.github.romacaio.dao;

import com.github.romacaio.model.veiculo.Veiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class JsonConfig {
    public static Gson criarGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Veiculo.class, new VeiculoAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }
}
