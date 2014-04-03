package com.pmrodrigues.ellasa.pagamentos.v1.paymentmethod;

import com.pmrodrigues.ellasa.pagamentos.Akatus;
import com.pmrodrigues.ellasa.pagamentos.AkatusOperation;
import com.pmrodrigues.ellasa.pagamentos.AkatusRequestBuilder;
import com.pmrodrigues.ellasa.pagamentos.AkatusResponseBuilder;

/**
 * Implementação da operação meios de pagamento da API da Akatus.
 * 
 * @author João Batista Neto
 */
public class PaymentMethodsOperation extends AkatusOperation {
	public PaymentMethodsOperation(Akatus akatus) {
		super(akatus);
	}

	protected AkatusRequestBuilder createDefaultRequestBuilder() {
		return new PaymentMethodsXMLRequestBuilder();
	}

	protected AkatusResponseBuilder createDefaultResponseBuilder() {
		return new PaymentMethodsXMLResponseBuilder();
	}

	public String getPath() {
		final StringBuilder pathBuilder = new StringBuilder();

		pathBuilder.append("/api/");
		pathBuilder.append(getVersion());
		pathBuilder.append("/meios-de-pagamento.xml");

		return pathBuilder.toString();
	}
}