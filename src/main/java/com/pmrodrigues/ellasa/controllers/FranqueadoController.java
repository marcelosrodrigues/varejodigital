package com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import static com.pmrodrigues.ellasa.Constante.LISTA_MEIOS_PAGAMENTO;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepostory;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.FranqueadoService;

@Resource
public class FranqueadoController {

	private final FranqueadoService service;

	private final TipoFranquiaRepository franquiaRepository;

	private final EstadoRepository estadoRepository;

	private final Result result;

	private final MeioPagamentoRepostory meioPagamentoRepository;

	public FranqueadoController(final FranqueadoService service,
			final TipoFranquiaRepository franquiaRepository,
			final EstadoRepository estadoRepository,
			final MeioPagamentoRepostory meioPagamentoRepostory,
			final Result result) {
		this.service = service;
		this.franquiaRepository = franquiaRepository;
		this.estadoRepository = estadoRepository;
		this.meioPagamentoRepository = meioPagamentoRepostory;
		this.result = result;
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
	public void avancar(final Franqueado franqueado, String indicacao,
			Long franquia, Long meiodepagamento) {

		final Contrato contrato = new Contrato();

		final Franqueado indicadoPor = service.findByCodigo(indicacao);
		if (indicadoPor != null) {
			indicadoPor.adicionar(franqueado);
		}

		final TipoFranquia tipo = franquiaRepository.findById(franquia);
		final MeioPagamento meiopagamento = meioPagamentoRepository
				.findById(meiodepagamento);

		contrato.setFranqueado(franqueado);
		contrato.setTipoFranquia(tipo);



		if (meiopagamento.eCartao()) {
			OrdemPagamentoCartaoCredito ordempagamento = new OrdemPagamentoCartaoCredito();
			ordempagamento.setValor(tipo.getValor());
			ordempagamento.setMeioPagamento(meiopagamento);
			ordempagamento.setContrato(contrato);

			result.forwardTo(this).avancar(ordempagamento);
		} else {
			OrdemPagamento ordempagamento = new OrdemPagamento();
		}



	}


	@Tiles(definition = "pagamento-cartao-credito-template")
	public void avancar(final OrdemPagamentoCartaoCredito ordempagamento) {
		result.include(Constante.ORDEM_PAGAMENTO, ordempagamento);
	}

	@Post
	@Path("/pagar-assinatura.html")
	public void pagar(final OrdemPagamentoCartaoCredito ordempagamento) {


	}


}
