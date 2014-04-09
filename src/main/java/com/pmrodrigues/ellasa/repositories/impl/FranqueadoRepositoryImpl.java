package com.pmrodrigues.ellasa.repositories.impl;

import static java.lang.String.format;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@Repository("FranqueadoRepository")
public class FranqueadoRepositoryImpl extends AbstractRepository<Franqueado>
		implements
			FranqueadoRepository {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(FranqueadoRepositoryImpl.class);

	/* (non-Javadoc)
	 * @see com.pmrodrigues.ellasa.repositories.impl.FranqueadoRepository#findByCodigo(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Franqueado findByCodigo(final String codigo) {

		try {
			LOGGER.debug(format("Procurando o franqueado pelo código %s", codigo));
			final Franqueado franqueado = (Franqueado) super.getEntityManager()
					.createNamedQuery("Franqueado.FindByCodigo")
					.setParameter("codigo", codigo).getSingleResult();

			LOGGER.debug(format("Franqueado %s encontrado", franqueado));
			return franqueado;
		} catch (NoResultException e) {
			return null;
		}
	}

}
