package com.github.romacaio.dao;

import com.github.romacaio.model.veiculo.Caminhao;
import com.github.romacaio.model.veiculo.Carro;
import com.github.romacaio.model.veiculo.Moto;
import com.github.romacaio.model.veiculo.Veiculo;
import com.google.gson.*;

import java.lang.reflect.Type;

public class VeiculoAdapter implements JsonSerializer<Veiculo>, JsonDeserializer<Veiculo> {

    @Override
    public JsonElement serialize(Veiculo veiculo, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(veiculo).getAsJsonObject();
        jsonObject.addProperty("tipo", veiculo.getClass().getSimpleName());
        return jsonObject;
    }

    @Override
    public Veiculo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String tipo = jsonObject.get("tipo").getAsString();
        return switch (tipo) {
            case "Carro" -> context.deserialize(jsonElement, Carro.class);
            case "Caminhao" -> context.deserialize(jsonElement, Caminhao.class);
            case "Moto" -> context.deserialize(jsonElement, Moto.class);
            default -> throw new JsonParseException("Tipo de veículo desconhecido: " + tipo);
        };
    }
}
