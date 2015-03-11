package com.pmrodrigues.ellasa.pagamentos.v1.status;

import com.pmrodrigues.ellasa.pagamentos.AkatusResponse;
import com.pmrodrigues.ellasa.pagamentos.v1.AkatusXMLResponseBuilder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Marceloo on 10/03/2015.
 */
public class StatusPagamentoXMLResponseBuilder extends AkatusXMLResponseBuilder {
    @Override
    protected AkatusResponse createAkatusResponse() {
        return new StatusPagamentoResponse();
    }

    @Override
    protected void readResponse(AkatusResponse akatusResponse) {
        try {
            super.readResponse(akatusResponse);

            final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));

            ((StatusPagamentoResponse) akatusResponse).setValor(new BigDecimal(getValor()).divide(new BigDecimal(100)));
            ((StatusPagamentoResponse) akatusResponse).setDataCriacao(format.parse(this.getDataCriacao()));
            ((StatusPagamentoResponse) akatusResponse).setAtualizacao(format.parse(this.getAtualizcao()));
            ((StatusPagamentoResponse) akatusResponse).setStatus(getStatus());
            ((StatusPagamentoResponse) akatusResponse).setDescription(getDescription());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private String getValor() {
        return getNodeTextValue("valor");
    }

    private String getDataCriacao() {
        return getNodeTextValue("data_criacao");
    }

    private String getAtualizcao() {
        return getNodeTextValue("data_status_atual");
    }

    private String getStatus() {
        return getNodeTextValue("status");
    }

    private String getDescription() {
        return getNodeTextValue("descricao");
    }
}

