package com.github.romacaio.model.cliente;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        Cliente other = (Cliente) obj;
        return cpf.equals(other.getCpf());
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente {" +
                "\n Nome: " + this.nome +
                "\n CPF: " + this.cpf +
                "\n Telefone: " + telefone +
                "\n Email: " + email +
                "\n}";
    }
}
