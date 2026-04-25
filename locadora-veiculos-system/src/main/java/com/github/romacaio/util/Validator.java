package com.github.romacaio.util;

public class Validator {

    public static boolean isNomeValido(String nome) {
        return nome.isBlank()
                && nome.length() > 3
                && nome.matches("(?<Nome>^[A-Za-z-A-ÿ]+)(?<sobreNome> [A-Za-z-A-ÿ]+)*$");

    }

    public static boolean isEmailValido(String email) {
        return email.matches("(^\\w+)(@\\w+)(\\.[a-zA-Z]+)+$");
    }

    public static boolean isPlacaValida(String placa) {
        return placa.matches("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$");
    }

    public static boolean isCpfValido(String cpf) {
        return cpf.matches("\\d{11}");
    }
}
