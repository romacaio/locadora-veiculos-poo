package com.github.romacaio.model;

public class Usuario {
    private String userName;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(String userName, String senha, TipoUsuario tipo) {
        this.userName = userName;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}
