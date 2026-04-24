package com.github.romacaio.model.usuario;

public class Usuario {
    private String userName;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(String userName, String senha, TipoUsuario tipo) {
        this.userName = userName;
        this.senha = senha;
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;
        Usuario other = (Usuario) obj;
        return this.userName.equalsIgnoreCase(other.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
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

    @Override
    public String toString() {
        return "Usuário {" +
                "\n UserName: " + userName +
                "\n Senha: " + senha +
                "\n Tipo Usuário: " + tipo +
                "\n}";
    }
}
