package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Holder;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import org.springframework.stereotype.Service;



@Service
public class BoletoService extends AbstractPagamentoService {

	@Override
	public void pagar(final OrdemPagamento pagamento) {

		final Transaction trans = criarTransacao(pagamento);
		trans.setPaymentMethod(PaymentMethod.BOLETO);
		trans.setHolder(new Holder());
		trans.getHolder().setDocument(
				pagamento.getContrato().getFranqueado().getCpf());
		trans.getHolder().setName(
				pagamento.getContrato().getFranqueado().getNomeCompleto());
		
		super.execute(pagamento);
	}


}



