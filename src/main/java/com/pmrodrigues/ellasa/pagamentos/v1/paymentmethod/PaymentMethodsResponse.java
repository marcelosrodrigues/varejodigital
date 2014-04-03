package com.pmrodrigues.ellasa.pagamentos.v1.paymentmethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pmrodrigues.ellasa.pagamentos.AkatusResponse;
import com.pmrodrigues.ellasa.pagamentos.entity.PaymentMethod;

public class PaymentMethodsResponse extends AkatusResponse {
	private final List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();

	public void addPaymentMethod(PaymentMethod paymentMethod) {
		paymentMethods.add(paymentMethod);
	}

	public Iterator<PaymentMethod> getPaymentMethodIterator() {
		return paymentMethods.iterator();
	}
}