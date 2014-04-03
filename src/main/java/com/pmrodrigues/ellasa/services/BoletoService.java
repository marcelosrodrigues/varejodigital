package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.pagamentos.v1.cart.CartResponse;



public class BoletoService extends AbstractPagamentoService {

	
	@Override
	public void pagar(final OrdemPagamento pagamento) {
		
		final Transaction trans = criarTransacao(pagamento);
		trans.setPaymentMethod(PaymentMethod.BOLETO);
		
		final CartResponse response = this.execute();
		if (!"erro".equalsIgnoreCase(response.getStatus())) {

			pagamento.setCodigo(response.getTransaction());
			pagamento.setStatus(response.getStatus());


		}
		

	}

}



