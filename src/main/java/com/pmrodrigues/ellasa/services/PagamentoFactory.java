package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class PagamentoFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private BoletoService boleto;

	@Autowired
	private CartaoCreditoService cartao;

	@Autowired
	private TEFService tef;

    @Autowired
    private MeioPagamentoRepository repository;

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

    public void pagar(final OrdemPagamento dadosPagamento) {

        final MeioPagamento meiopagamento = repository.findById(dadosPagamento.getMeioPagamento().getId());
        dadosPagamento.setMeioPagamento(meiopagamento);
        getPagamentoService(meiopagamento.getTipo()).pagar(dadosPagamento);

    }
}
