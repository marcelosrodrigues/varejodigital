package com.pmrodrigues.ellasa.services;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.pagamentos.entity.Holder;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;

@Service
public class CartaoCreditoService extends AbstractPagamentoService {

	@Override
	public void pagar(final OrdemPagamento pagamento) {

		final Transaction trans = criarTransacao(pagamento);
		final OrdemPagamentoCartaoCredito dadoscartao = (OrdemPagamentoCartaoCredito) pagamento;

		trans.setPaymentMethod(dadoscartao.getMeioPagamento().getTipo());

		trans.setExpiration(DateFormatUtils.format(
				dadoscartao.getDataExpiracao(), "MM/yyyy"));
		trans.setNumber(dadoscartao.getNumero());
		trans.setSecurityNumber(dadoscartao.getCodigoSeguranca());

		trans.setHolder(new Holder());
		trans.getHolder().setDocument(dadoscartao.getCPF());
		trans.getHolder().setName(dadoscartao.getPortador());
		trans.getHolder().setPhone(dadoscartao.getTelefone());

		super.execute(pagamento);

	}

}
