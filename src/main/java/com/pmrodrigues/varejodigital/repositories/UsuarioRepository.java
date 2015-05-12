package com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Usuario;

import java.util.List;

public interface UsuarioRepository extends Repository<Usuario> {

    Usuario findByEmail(String email);

    List<Usuario> listByNomeOrEmail(final String nome);
}