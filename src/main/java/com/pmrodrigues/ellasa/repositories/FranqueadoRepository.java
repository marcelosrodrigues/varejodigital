package com.pmrodrigues.ellasa.repositories;

import static java.lang.String.format;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Franqueado;

@Repository
public class FranqueadoRepository extends AbstractRepository<Franqueado> {

	private static final long serialVersionUID = 1L;

	private static final Logger logging = Logger
			.getLogger(FranqueadoRepository.class);

	public Franqueado findByCodigo(final String codigo) {

		logging.debug(format("Procurando o franqueado pelo código %s", codigo));
		final Franqueado franqueado = (Franqueado) super.getEntityManager()
				.createNamedQuery("Franqueado.FindByCodigo")
				.setParameter("codigo", codigo).getSingleResult();

		logging.debug(format("Franqueado %s encontrado", franqueado));
		return franqueado;
	}

}
