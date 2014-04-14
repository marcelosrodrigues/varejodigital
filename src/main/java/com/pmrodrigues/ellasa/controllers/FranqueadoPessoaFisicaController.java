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
public class FranqueadoPessoaFisicaController {

	private final FranqueadoService service;

	private final TipoFranquiaRepository franquiaRepository;

	private final EstadoRepository estadoRepository;

	private final Result result;

	private final MeioPagamentoRepository meioPagamentoRepository;

	private final Validator validator;

	public FranqueadoPessoaFisicaController(final FranqueadoService service,
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
			final Franqueado indicacao, final TipoFranquia franquia,
			final MeioPagamento meiodepagamento) {

		validator.validate(franqueado);
		validator.validate(franqueado.getEndereco());

		validator.checking(new Validations() {
			{
				that(franquia != null, "franquia", "error.required",
						i18n("franquia.field"));
				that(meiodepagamento != null, "meiodepagamento",
						"error.required", i18n("meiodepagamento.field"));
				that(GenericValidator.isEmail(franqueado.getEmail()), "email",
						"error.email.invalid", i18n("email.field"));
				that(indicacao != null, "indicacao", "error.notfound",
						i18n("indicacao.field"));
				that(!GenericValidator.isBlankOrNull(franqueado
						.getResidencial().getDdd())
						&& !GenericValidator.isBlankOrNull(franqueado
								.getResidencial().getNumero()), "telefone",
						"error.required", i18n("telefone.field"));
			}
		});

		validator.onErrorForwardTo(this).iniciar();

		indicacao.adicionar(franqueado);

		final Contrato contrato = new Contrato(franqueado, franquia);

		if (meiodepagamento.eCartao()) {
			final OrdemPagamentoCartaoCredito ordempagamento = new OrdemPagamentoCartaoCredito(
					meiodepagamento, contrato, franquia.getValor());

			result.forwardTo(PagamentoCartaoCreditoController.class).avancar(
					ordempagamento);
		} else {
			final OrdemPagamento ordempagamento = new OrdemPagamento(
					meiodepagamento, contrato, franquia.getValor());

			result.forwardTo(PagamentoController.class)
					.concluir(ordempagamento);
		}

	}

}
