package com.github.romacaio.dao;

import java.util.List;

public interface Persistencia<T> {

    void salvar(List<T> dados);

    List<T> carregar();
}
