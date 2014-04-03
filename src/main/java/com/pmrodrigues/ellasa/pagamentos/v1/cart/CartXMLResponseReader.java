package com.pmrodrigues.ellasa.pagamentos.v1.cart;

import com.pmrodrigues.ellasa.pagamentos.AkatusResponse;
import com.pmrodrigues.ellasa.pagamentos.v1.AkatusXMLResponseBuilder;

class CartXMLResponseReader extends AkatusXMLResponseBuilder {
	protected AkatusResponse createAkatusResponse() {
		return new CartResponse();
	}

	@Override
	protected void readResponse(AkatusResponse akatusResponse) {
		super.readResponse(akatusResponse);

		((CartResponse) akatusResponse).setCart(getCart());
		((CartResponse) akatusResponse).setReference(getReference());
		((CartResponse) akatusResponse).setTransaction(getTransaction());
		((CartResponse) akatusResponse).setReturnURL(getReturnURL());

	}

	private String getCart() {
		return getNodeTextValue("carrinho");
	}

	private String getReference() {
		return getNodeTextValue("referencia");
	}

	private String getTransaction() {
		return getNodeTextValue("transacao");
	}

	private String getReturnURL() {
		return getNodeTextValue("url_retorno");
	}
}