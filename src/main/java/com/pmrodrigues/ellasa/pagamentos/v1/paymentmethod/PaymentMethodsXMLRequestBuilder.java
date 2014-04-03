package com.pmrodrigues.ellasa.pagamentos.v1.paymentmethod;

import org.w3c.dom.Element;

import com.pmrodrigues.ellasa.pagamentos.AkatusOperation;
import com.pmrodrigues.ellasa.pagamentos.v1.AkatusXMLRequestBuilder;

class PaymentMethodsXMLRequestBuilder extends AkatusXMLRequestBuilder {
	public String build(AkatusOperation operation) {
		createDocument();
		createPaymentMethods((PaymentMethodsOperation) operation);

		return transformDocument();
	}

	private void createPaymentMethods(PaymentMethodsOperation operation) {
		final Element paymentMethodsElement = document
				.createElement("meios_de_pagamento");

		paymentMethodsElement.appendChild(createAccountElement("correntista",
				operation));

		document.appendChild(paymentMethodsElement);
	}
}