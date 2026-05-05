package com.github.romacaio.controller;

import com.github.romacaio.dao.UsuarioDao;
import com.github.romacaio.model.usuario.TipoUsuario;
import com.github.romacaio.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioController {
    private UsuarioDao usuarioDao;
    private List<Usuario> usuarios;
    private Usuario usuarioLogado;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDao();
        this.usuarios = usuarioDao.carregar();
        inicializarAdminPadrao();
    }

    public void cadastrarUsuario(Usuario usuario) {
        boolean existe = usuarios.stream()
                .anyMatch(user -> user.equals(usuario));

        if (existe) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        usuarios.add(usuario);
        usuarioDao.salvar(usuarios);
    }

    public Usuario loginUsuario(String userName, String senha) {
        Optional<Usuario> busca = usuarios.stream()
                .filter(user -> user.getUserName().equals(userName)
                        && user.getSenha().equals(senha))
                .findFirst();

        if (busca.isEmpty()) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

        this.usuarioLogado = busca.get();
        return usuarioLogado;
    }

    private void inicializarAdminPadrao() {
        if (usuarios.isEmpty()) {
            usuarios.add(
                    new Usuario("admin", "123", TipoUsuario.ADMIN)
            );

            usuarioDao.salvar(usuarios);
            usuarios = usuarioDao.carregar();
        }
    }

    public boolean isAdmin() {
        return usuarioLogado != null
                && usuarioLogado.getTipo() == TipoUsuario.ADMIN;
    }

    public boolean isGerente() {
        return usuarioLogado != null
                && usuarioLogado.getTipo() == TipoUsuario.GERENTE;
    }

    public boolean isAtendente() {
        return usuarioLogado != null
                && usuarioLogado.getTipo() == TipoUsuario.ATENDENTE;
    }

    public void logout() {
        this.usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return this.usuarioLogado;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
