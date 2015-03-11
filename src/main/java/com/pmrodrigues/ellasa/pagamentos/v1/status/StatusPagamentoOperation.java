package com.pmrodrigues.ellasa.pagamentos.v1.status;

import com.pmrodrigues.ellasa.pagamentos.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by Marceloo on 10/03/2015.
 */
public class StatusPagamentoOperation extends AkatusOperation {
    private final String referencia;

    /**
     * Constroi o objeto que representa a operação passando o wrapper da API.
     *
     * @param akatus     é a instância do wrapper da API da {@link com.pmrodrigues.ellasa.pagamentos.Akatus}
     * @param referencia
     */
    public StatusPagamentoOperation(Akatus akatus, String referencia) {
        super(akatus);
        this.referencia = referencia;
    }

    @Override
    protected AkatusRequestBuilder createDefaultRequestBuilder() {
        return new StatusPagamentoXMLRequestBuilder();
    }

    @Override
    protected AkatusResponseBuilder createDefaultResponseBuilder() {
        return new StatusPagamentoXMLResponseBuilder();
    }

    @Override
    public String getPath() {

        return format("/api/%s/transacao-simplificada/%s.xml?email=%s&api_key=%s",
                getVersion(),
                this.referencia.trim(),
                this.getAccount().getEmail().trim(),
                this.getAccount().getApiKey().trim());

    }

    @Override
    public AkatusResponse execute(AkatusRequestBuilder requestBuilder,
                                  AkatusResponseBuilder responseBuilder) {

        AkatusResponse response = new AkatusResponse();

        try {
            final URL url = new URL(akatus.getHost() + getPath());

            try {
                response = responseBuilder.build(readResponse(url.openStream()));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        } catch (final Exception e) {
            Logger.getLogger(AkatusOperation.class.getName()).throwing(
                    this.getClass().getName(), "execute", e);
        }

        return response;
    }

    private String readResponse(InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));

        final StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        return stringBuffer.toString();
    }
}
