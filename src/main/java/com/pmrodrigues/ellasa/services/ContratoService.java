package com.pmrodrigues.ellasa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.repositories.ContratoRepository;
import com.pmrodrigues.ellasa.repositories.OrdemPagamentoRepository;

@Service
public class ContratoService {

	@Autowired
	private FranqueadoService service;

	@Autowired
	private PagamentoFactory factory;

	@Autowired
	private OrdemPagamentoRepository ordempagamentoRepository;

	@Autowired
	private ContratoRepository repository;


	@Transactional(propagation = Propagation.REQUIRED)
	public void assinar(final OrdemPagamento ordempagamento) {

		final PagamentoService caixa = factory
				.getPagamentoService(PaymentMethod.CARTAO_VISA);

		service.adicionar(ordempagamento.getContrato().getFranqueado());
		repository.add(ordempagamento.getContrato());
		ordempagamentoRepository.add(ordempagamento);
		caixa.pagar(ordempagamento);

	}
}
