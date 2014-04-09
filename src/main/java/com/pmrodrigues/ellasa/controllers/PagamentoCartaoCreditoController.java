package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.ContratoService;

@Resource
public class PagamentoCartaoCreditoController {

	private final Result result;

	private final MeioPagamentoRepository meioPagamentoRepository;

	private final ContratoService service;

	private final Validator validator;

	private final TipoFranquiaRepository franquiaRepository;

	public PagamentoCartaoCreditoController(
			final TipoFranquiaRepository franquiaRepository,
			final EstadoRepository estadoRepository,
			final MeioPagamentoRepository meioPagamentoRepostory,
			final ContratoService service, final Result result,
			final Validator validator) {
		this.service = service;
		this.franquiaRepository = franquiaRepository;
		this.meioPagamentoRepository = meioPagamentoRepostory;
		this.result = result;
		this.validator = validator;
	}

	@Tiles(definition = "pagamento-cartao-credito-template")
	public void avancar(final OrdemPagamentoCartaoCredito ordempagamento) {
		result.include(Constante.ORDEM_PAGAMENTO, ordempagamento);
	}

	@Post
	@Path("/pagar-assinatura.html")
	@Tiles(definition = "boas-vindas-template")
	public void pagar(final OrdemPagamentoCartaoCredito ordempagamento,
			Long tipoFranquia, Long meioPagamento) {

		validator.validate(ordempagamento);

		validator.onErrorForwardTo(this).avancar(ordempagamento);

		final TipoFranquia tipo = franquiaRepository.findById(tipoFranquia);
		ordempagamento.getContrato().setTipoFranquia(tipo);
		ordempagamento.setMeioPagamento(meioPagamentoRepository
				.findById(meioPagamento));
		ordempagamento.setDescricao("ASSINATURA DE CONTRATO");
		ordempagamento.setValor(tipo.getValor());
		service.assinar(ordempagamento);

		result.include(Constante.FRANQUEADO, ordempagamento.getContrato()
				.getFranqueado());

	}
}
