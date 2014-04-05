package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.repositories.OrdemPagamentoRepository;

@Repository("OrdemPagamentoRepository")
public class OrdemPagamentoRepositoryImpl
		extends
			AbstractRepository<OrdemPagamento> implements OrdemPagamentoRepository {

	private static final long serialVersionUID = 1L;

}
