package com.pmrodrigues.ellasa.controllers;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.services.ContratoService;
import com.pmrodrigues.ellasa.utilities.HTMLReader;

@Resource
public class PagamentoController {

	private final Result result;

	private final ContratoService service;

	public PagamentoController(
final ContratoService service,
			final Result result) {
		this.service = service;
		this.result = result;
	}

	@Tiles(definition = "boas-vindas-boleto-template")
	public void concluir(final OrdemPagamento ordempagamento) {
		final Contrato contrato = ordempagamento.getContrato();
		final TipoFranquia tipo = contrato.getTipoFranquia();
		final Franqueado franqueado = contrato.getFranqueado();

		ordempagamento.setDescricao("ASSINATURA DE CONTRATO");
		ordempagamento.setValor(tipo.getValor());
		service.assinar(ordempagamento);

		result.include(Constante.FRANQUEADO, franqueado);
		final HTMLReader reader = new HTMLReader();
		try {
			if (ordempagamento.getMeioPagamento().getTipo() == PaymentMethod.BOLETO) {
				result.include(
						Constante.BOLETO,
						reader.getElement("container",
								new URL(ordempagamento.getDocumento())));
			} else {
				result.include(Constante.TEF, ordempagamento.getDocumento());
			}
		} catch (IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
