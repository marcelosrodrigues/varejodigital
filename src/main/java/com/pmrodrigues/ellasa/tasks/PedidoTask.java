package com.pmrodrigues.ellasa.tasks;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.pagamentos.v1.status.StatusPagamentoResponse;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import com.pmrodrigues.ellasa.services.StatusPagamentoIntegrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 10/03/2015.
 */
@Component
public class PedidoTask {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private StatusPagamentoIntegrationService service;

    private static final Logger logging = Logger.getLogger(PedidoTask.class);

    public void update() {

        logging.info("atualizando o status dos pedidos recebidos");

        final List<Pedido> pedidos = repository.listPedidosAguardandoPagamento();
        for (final Pedido pedido : pedidos) {

            logging.debug(format("buscando o status do pedido %s", pedido.getCodigoTransacao()));

            final StatusPagamentoResponse status = service.recuperarStatus(pedido.getCodigoReferencia());
            pedido.setDataAprovacao(status.getAtualizacao());
            final String statusDoPedido = status.getStatus().replace(' ', '_').toUpperCase();

            logging.debug(format("O status do pedido %s junto a akatus é %s", pedido.getCodigoTransacao(), statusDoPedido));

            pedido.setStatus(StatusPagamento.valueOf(statusDoPedido));
            if ("ERRO".equalsIgnoreCase(statusDoPedido)) {
                pedido.setMotivo(status.getDescription());
            }

            repository.set(pedido);
        }

        logging.info("todos os pedidos foram atualizados");

    }

}
