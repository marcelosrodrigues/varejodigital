package com.pmrodrigues.ellasa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Holder;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;



@Service
public class BoletoService extends AbstractPagamentoService {

	@Autowired
	private EmailService email;
	
	@Override
	public void pagar(final OrdemPagamento pagamento) {
		
		final Transaction trans = criarTransacao(pagamento);
		trans.setPaymentMethod(PaymentMethod.BOLETO);
		trans.setHolder(new Holder());
		trans.getHolder().setDocument(
				pagamento.getContrato().getFranqueado().getDocumento());
		trans.getHolder().setName(
				pagamento.getContrato().getFranqueado().getNome());
		
		super.execute(pagamento);
	}


}



