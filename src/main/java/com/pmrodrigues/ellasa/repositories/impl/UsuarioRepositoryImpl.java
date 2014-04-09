package com.pmrodrigues.ellasa.repositories.impl;

import static java.lang.String.format;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@Repository("UsuarioRepository")
public class UsuarioRepositoryImpl extends AbstractRepository<Usuario> implements UsuarioRepository {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(UsuarioRepositoryImpl.class);

	/* (non-Javadoc)
	 * @see com.pmrodrigues.ellasa.repositories.impl.UsuarioRepository#findByEmail(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Usuario findByEmail(final String email) {

		LOGGER.debug(format("Pesquisando usuário pelo email %s", email));

		final Usuario usuario = (Usuario) super.getEntityManager()
				.createNamedQuery("Usuario.FindByEmail")
				.setParameter("email", email).getSingleResult();

		LOGGER.debug(format("Usuario %s encontrado", usuario));

		return usuario;
	}

}
