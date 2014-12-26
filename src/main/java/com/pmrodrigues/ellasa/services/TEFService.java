package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Holder;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TEFService extends AbstractPagamentoService {

	@Autowired
	private EmailService email;

	@Override
	public void pagar(final OrdemPagamento pagamento) {
		final Transaction trans = criarTransacao(pagamento);
		trans.setPaymentMethod(pagamento.getMeioPagamento().getTipo());
		trans.setHolder(new Holder());
		trans.getHolder().setDocument(
				pagamento.getContrato().getFranqueado().getCpf());
		trans.getHolder().setName(
				pagamento.getContrato().getFranqueado().getNomeCompleto());

		super.execute(pagamento);
	}
}
