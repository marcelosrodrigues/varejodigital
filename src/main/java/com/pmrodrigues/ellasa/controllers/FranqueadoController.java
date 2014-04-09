package com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import static com.pmrodrigues.ellasa.Constante.LISTA_MEIOS_PAGAMENTO;

import org.apache.commons.validator.GenericValidator;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;

import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.FranqueadoService;

@Resource
public class FranqueadoController {

	private final FranqueadoService service;

	private final TipoFranquiaRepository franquiaRepository;

	private final EstadoRepository estadoRepository;

	private final Result result;

	private final MeioPagamentoRepository meioPagamentoRepository;

	private final Validator validator;

	public FranqueadoController(final FranqueadoService service,
			final TipoFranquiaRepository franquiaRepository,
			final EstadoRepository estadoRepository,
			final MeioPagamentoRepository meioPagamentoRepostory,
			final Result result, final Validator validator) {
		this.service = service;
		this.franquiaRepository = franquiaRepository;
		this.estadoRepository = estadoRepository;
		this.meioPagamentoRepository = meioPagamentoRepostory;
		this.result = result;
		this.validator = validator;
	}

	@Get
	@Path("/seja-um-franqueado.html")
	@Tiles(definition = "seja-um-franqueado-template")
	public void iniciar() {

		result.include(LISTA_ESTADOS, estadoRepository.list());
		result.include(LISTA_FRANQUIAS, franquiaRepository.list());
		result.include(LISTA_MEIOS_PAGAMENTO, meioPagamentoRepository.list());

	}

	@Post
	@Path("/seja-um-franqueado.html")
	public void avancar(final FranqueadoPessoaFisica franqueado,
			final String indicacao, final Long franquia,
			final Long meiodepagamento) {

		validator.validate(franqueado);
		validator.validate(franqueado.getEndereco());

		validator.checking(new Validations() {
			{
				that(franquia != null && franquia > 0, "franquia",
						"error.required", i18n("franquia.field"));
				that(meiodepagamento != null && meiodepagamento > 0,
						"meiodepagamento", "error.required",
						i18n("meiodepagamento.field"));
				that(GenericValidator.isEmail(franqueado.getEmail()), "email",
						"error.email.invalid", i18n("email.field"));
			}
		});

		validator.onErrorForwardTo(this).iniciar();

		final Franqueado indicadoPor = service.findByCodigo(indicacao);
		if (indicadoPor != null) {
			indicadoPor.adicionar(franqueado);
		}

		final TipoFranquia tipo = franquiaRepository.findById(franquia);
		final MeioPagamento meiopagamento = meioPagamentoRepository
				.findById(meiodepagamento);

		final Contrato contrato = new Contrato(franqueado, tipo);

		if (meiopagamento.eCartao()) {
			final OrdemPagamentoCartaoCredito ordempagamento = new OrdemPagamentoCartaoCredito(
					meiopagamento, contrato, tipo.getValor());

			result.forwardTo(PagamentoCartaoCreditoController.class).avancar(
					ordempagamento);
		} else {
			final OrdemPagamento ordempagamento = new OrdemPagamento(
					meiopagamento, contrato, tipo.getValor());

			result.forwardTo(PagamentoController.class)
					.concluir(ordempagamento);
		}

	}


}
