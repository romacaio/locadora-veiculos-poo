package com.github.romacaio.util;

import java.time.Year;

public class Validator {

    public static boolean isNomeValido(String nome) {
        return !nome.isBlank()
                && nome.length() > 3
                && nome.matches("(?<Nome>^[A-Za-z-A-ÿ]+)(?<sobreNome> [A-Za-z-A-ÿ]+)*$");
    }

    public static boolean isEmailValido(String email) {
        return email.matches("(^\\w+)(@\\w+)(\\.[a-zA-Z]+)+$");
    }

    public static boolean isTelefoneValido(String telefone) {
        return telefone.matches("^\\d{10}");
    }

    public static boolean isCpfValido(String cpf) {
        return cpf.matches("\\d{11}");
    }

    public static boolean isPlacaValida(String placa) {
        return placa.matches("^[A-Z-a-z]{3}[0-9][A-Za-z0-9][0-9]{2}$");
    }

    public static boolean isAnoVeiculoValido(int ano) {
        Year anoInput = Year.of(ano);
        Year anoAtual = Year.now();

        return anoInput.isBefore(anoAtual) || anoInput.equals(anoAtual);
    }

}
