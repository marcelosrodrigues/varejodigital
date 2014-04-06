package com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import static com.pmrodrigues.ellasa.Constante.LISTA_MEIOS_PAGAMENTO;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

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
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.ContratoService;
import com.pmrodrigues.ellasa.services.FranqueadoService;
import com.pmrodrigues.ellasa.utilities.HTMLReader;

@Resource
public class FranqueadoController {

	private final FranqueadoService service;

	private final TipoFranquiaRepository franquiaRepository;

	private final EstadoRepository estadoRepository;

	private final Result result;

	private final MeioPagamentoRepository meioPagamentoRepository;

	private final ContratoService contratoService;

	public FranqueadoController(final FranqueadoService service,
			final TipoFranquiaRepository franquiaRepository,
			final EstadoRepository estadoRepository,
			final MeioPagamentoRepository meioPagamentoRepostory,
			final ContratoService contratoService,
			final Result result) {
		this.service = service;
		this.franquiaRepository = franquiaRepository;
		this.estadoRepository = estadoRepository;
		this.meioPagamentoRepository = meioPagamentoRepostory;
		this.contratoService = contratoService;
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
	public void avancar(final Franqueado franqueado, final String indicacao,
			final Long franquia, final Long meiodepagamento) {

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
			final OrdemPagamentoCartaoCredito ordempagamento = new OrdemPagamentoCartaoCredito();
			ordempagamento.setValor(tipo.getValor());
			ordempagamento.setMeioPagamento(meiopagamento);
			ordempagamento.setContrato(contrato);

			result.forwardTo(this).avancar(ordempagamento);
		} else {
			final OrdemPagamento ordempagamento = new OrdemPagamento();
			ordempagamento.setValor(tipo.getValor());
			ordempagamento.setMeioPagamento(meiopagamento);
			ordempagamento.setContrato(contrato);
			result.forwardTo(this).concluir(ordempagamento);
		}



	}

	@Tiles(definition = "boas-vindas-boleto-template")
	public void concluir(final OrdemPagamento ordempagamento) {
		final TipoFranquia tipo = ordempagamento.getContrato()
				.getTipoFranquia();
		ordempagamento.setDescricao("ASSINATURA DE CONTRATO");
		ordempagamento.setValor(tipo.getValor());
		contratoService.assinar(ordempagamento);

		result.include(Constante.FRANQUEADO, ordempagamento.getContrato()
				.getFranqueado());
		HTMLReader reader = new HTMLReader();
		try {
			if (ordempagamento.getMeioPagamento().getTipo() == PaymentMethod.BOLETO) {
				result.include(
						"boleto",
						reader.getElement("container",
								new URL(ordempagamento.getDocumento())));
			} else {
				result.include("tef", ordempagamento.getDocumento());
			}
		} catch (IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		final TipoFranquia tipo = franquiaRepository.findById(tipoFranquia);
		ordempagamento.getContrato().setTipoFranquia(tipo);
		ordempagamento.setMeioPagamento(meioPagamentoRepository
				.findById(meioPagamento));
		ordempagamento.setDescricao("ASSINATURA DE CONTRATO");
		ordempagamento.setValor(tipo.getValor());
		contratoService.assinar(ordempagamento);

		result.include(Constante.FRANQUEADO, ordempagamento.getContrato()
				.getFranqueado());

	}


}
