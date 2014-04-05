package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;

@Repository("MeioPagamentoRepostory")
public class MeioPagamentoRepositoryImpl extends AbstractRepository<MeioPagamento> implements MeioPagamentoRepository {

	private static final long serialVersionUID = 1L;

}
