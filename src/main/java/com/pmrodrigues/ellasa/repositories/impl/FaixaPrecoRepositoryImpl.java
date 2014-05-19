package com.pmrodrigues.ellasa.repositories.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.FaixaPreco;
import com.pmrodrigues.ellasa.repositories.FaixaRepository;

@Repository
public class FaixaPrecoRepositoryImpl extends AbstractRepository<FaixaPreco> implements FaixaRepository{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger
			.getLogger(FaixaPrecoRepositoryImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FaixaPreco> list() {
		LOGGER.debug("Listando todos os valores de faixa de preço do banco de dados");
		
		final List<FaixaPreco> all = this.getSession()
				.getNamedQuery("FaixaPreco.All")
				.list();
		return all;
	}
}
