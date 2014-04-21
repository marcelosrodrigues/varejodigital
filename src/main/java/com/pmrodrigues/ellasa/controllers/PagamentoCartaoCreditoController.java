package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.controllers.sessionscope.OrdemPagamentoSession;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.services.ContratoService;

@Resource
public class PagamentoCartaoCreditoController {

	private final Result result;

	private final ContratoService service;

	private final Validator validator;

	private final OrdemPagamentoSession session;

	public PagamentoCartaoCreditoController(
			final ContratoService service, final Result result,
			final Validator validator, final OrdemPagamentoSession session) {
		this.service = service;
		this.result = result;
		this.validator = validator;
		this.session = session;
	}

	@Tiles(definition = "pagamento-cartao-credito-template")
	public void avancar(final OrdemPagamentoCartaoCredito ordempagamento) {
		result.include(Constante.ORDEM_PAGAMENTO, ordempagamento);
		session.toSession(ordempagamento);
	}

	@Post
	@Path("/pagar-assinatura.html")
	@Tiles(definition = "boas-vindas-template")
	public void pagar(final OrdemPagamentoCartaoCredito ordempagamento) {

		OrdemPagamento ordem = session.fromSession();
		ordempagamento.setContrato(ordem.getContrato());
		validator.validate(ordempagamento);

		validator.onErrorForwardTo(this).avancar(ordempagamento);

		ordempagamento.setDescricao("ASSINATURA DE CONTRATO");

		service.assinar(ordempagamento);

		result.include(Constante.FRANQUEADO, ordempagamento.getContrato()
				.getFranqueado());

	}
}
