package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Usuario;

public interface UsuarioRepository extends Repository<Usuario> {

	public abstract Usuario findByEmail(String email);

}