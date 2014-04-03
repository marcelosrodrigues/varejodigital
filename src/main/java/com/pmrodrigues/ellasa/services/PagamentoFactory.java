package com.pmrodrigues.ellasa.services;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.pmrodrigues.ellasa.models.Boleto;
import com.pmrodrigues.ellasa.models.CartaoCredito;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.TEF;

@Service
public class PagamentoFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public final PagamentoService getPagamentoService(
			final MeioPagamento meiopagamento) {

		if (meiopagamento instanceof Boleto) {
			return new BoletoService();
		} else if (meiopagamento instanceof TEF) {
			return null;
		} else if (meiopagamento instanceof CartaoCredito) {
			return null;
		} else {
			throw new RuntimeException("Meio de pagamento inválido");
		}

	}

}
