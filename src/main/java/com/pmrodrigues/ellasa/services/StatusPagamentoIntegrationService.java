package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.pagamentos.Akatus;
import com.pmrodrigues.ellasa.pagamentos.v1.status.StatusPagamentoOperation;
import com.pmrodrigues.ellasa.pagamentos.v1.status.StatusPagamentoResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * Created by Marceloo on 10/03/2015.
 */
@Service
public class StatusPagamentoIntegrationService {

    @Value("${AUTH_USER}")
    private String user;

    @Value("${AUTH_PASSWORD}")
    private String key;

    @Value("${AKATUR_URL}")
    private String enviroment;

    private static final Logger logging = Logger.getLogger(StatusPagamentoIntegrationService.class);

    protected Recebedor getRecebedor() {
        return new Recebedor(user, key);
    }

    public StatusPagamentoResponse recuperarStatus(final String referencia) {


        logging.debug(format("recuperando o status da compra %s junto a akatus", referencia));

        try {
            final Recebedor recebedor = this.getRecebedor();
            final Akatus akatus = new Akatus(Akatus.Environment.valueOf(enviroment),
                    recebedor.getEmail(),
                    recebedor.getApiKey());

            final StatusPagamentoOperation status = akatus.status(referencia);
            return (StatusPagamentoResponse) status.execute();
        } finally {
            logging.debug(format("execução da pesquisa de status da compra %s concluída com sucesso", referencia));
        }

    }
}
