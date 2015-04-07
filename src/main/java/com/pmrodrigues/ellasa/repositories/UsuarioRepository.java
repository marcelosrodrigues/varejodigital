package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Usuario;

import java.util.List;

public interface UsuarioRepository extends Repository<Usuario> {

    Usuario findByEmail(String email);

    List<Usuario> listByNomeOrEmail(final String nome);
}