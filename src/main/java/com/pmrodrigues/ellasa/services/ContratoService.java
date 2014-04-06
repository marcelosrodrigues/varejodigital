package com.pmrodrigues.ellasa.services;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.repositories.ContratoRepository;
import com.pmrodrigues.ellasa.repositories.OrdemPagamentoRepository;

@Service
public class ContratoService {

	@Resource(name = "FranqueadoService")
	private FranqueadoService service;

	@Autowired
	private PagamentoFactory factory;

	@Resource(name = "OrdemPagamentoRepository")
	private OrdemPagamentoRepository ordempagamentoRepository;

	@Resource(name = "ContratoRepository")
	private ContratoRepository repository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void assinar(final OrdemPagamento ordempagamento) {

		final PagamentoService caixa = factory
				.getPagamentoService(ordempagamento.getMeioPagamento()
						.getTipo());

		caixa.pagar(ordempagamento);
		if (ordempagamento.isSucesso()) {
			service.adicionar(ordempagamento.getContrato().getFranqueado());
			repository.add(ordempagamento.getContrato());
			ordempagamentoRepository.add(ordempagamento);
		} else {
			throw new RuntimeException(ordempagamento.getMotivo());
		}


	}
}
