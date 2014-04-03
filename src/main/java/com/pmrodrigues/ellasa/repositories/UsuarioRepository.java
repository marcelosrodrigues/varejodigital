package com.pmrodrigues.ellasa.repositories;

import static java.lang.String.format;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Usuario;

@Repository
public class UsuarioRepository extends AbstractRepository<Usuario> {

	private static final long serialVersionUID = 1L;

	private static final Logger logging = Logger
			.getLogger(UsuarioRepository.class);

	public Usuario findByEmail(final String email) {

		logging.debug(format("Pesquisando usuário pelo email %s", email));

		final Usuario usuario = (Usuario) super.getEntityManager()
				.createNamedQuery("Usuario.FindByEmail")
				.setParameter("email", email).getSingleResult();

		logging.debug(format("Usuario %s encontrado", usuario));

		return usuario;
	}

}
