package com.github.romacaio.service;

import com.github.romacaio.model.usuario.TipoUsuario;
import com.github.romacaio.model.usuario.Usuario;

import java.util.Set;

public class PermissaoService {

    public static void verificar(Usuario usuario, TipoUsuario... tiposPermitidos) {
        if (usuario == null) {
            throw new SecurityException("Usuário não logado");
        }

        Set<TipoUsuario> permitidos = Set.of(tiposPermitidos);

        if (!permitidos.contains(usuario.getTipo())) {
            throw new SecurityException("Acesso negado");
        }
    }
}
