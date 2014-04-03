package com.pmrodrigues.ellasa.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;

@Service
public class PagamentoFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private BoletoService boleto;

	@Autowired
	private CartaoCreditoService cartao;

	@Autowired
	private TEFService tef;

	public final PagamentoService getPagamentoService(
			final PaymentMethod method) {
		
		
		if (method == PaymentMethod.BOLETO) {
			return boleto;
		} else if (method == PaymentMethod.TEF_ITAU || method == PaymentMethod.TEF_BRADESCO) {
			return tef;
		} else if (method == PaymentMethod.CARTAO_AMEX
				|| method == PaymentMethod.CARTAO_DINERS
				|| method == PaymentMethod.CARTAO_ELO
				|| method == PaymentMethod.CARTAO_MASTER
				|| method == PaymentMethod.CARTAO_VISA) {
			return cartao;
		} else {
			throw new RuntimeException("Meio de pagamento inválido");
		}

	}

}
