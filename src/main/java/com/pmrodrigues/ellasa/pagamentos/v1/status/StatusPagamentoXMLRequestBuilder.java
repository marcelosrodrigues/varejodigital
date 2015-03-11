package com.pmrodrigues.ellasa.pagamentos.v1.status;

import com.pmrodrigues.ellasa.pagamentos.AkatusOperation;
import com.pmrodrigues.ellasa.pagamentos.v1.AkatusXMLRequestBuilder;

/**
 * Created by Marceloo on 10/03/2015.
 */
public class StatusPagamentoXMLRequestBuilder extends AkatusXMLRequestBuilder {
    @Override
    public String build(AkatusOperation operation) {
        createDocument();

        return transformDocument();
    }
}
