package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Marceloo on 14/10/2014.
 */
@Repository("PedidoRepository")
public class PedidoRepositoryImpl extends AbstractRepository<Pedido> implements PedidoRepository {
    @Override
    public List<Pedido> listPedidosAguardandoPagamento() {
        return this.getSession().createCriteria(Pedido.class)
                .add(Restrictions.eq("status", StatusPagamento.AGUARDANDO_PAGAMENTO))
                .addOrder(Order.asc("dataCompra"))
                .list();
    }

    @Override
    public ResultList<Pedido> search(final Pedido pedido) {
        return this.search(pedido, 0);
    }

    @Override
    public ResultList<Pedido> search(final Pedido pedido, final Integer page) {
        final Criteria criteria = this.getSession().createCriteria(Pedido.class)
                .createAlias("cliente", "cliente")
                .setFetchMode("cliente", FetchMode.JOIN)
                .setFetchMode("itens", FetchMode.JOIN)
                .addOrder(Order.desc("dataCompra"));

        if (pedido != null) {
            if (pedido.getCliente() != null) {
                if (!GenericValidator.isBlankOrNull(pedido.getCliente().getPrimeiroNome())) {
                    criteria.add(Restrictions.like("cliente.primeiroNome", pedido.getCliente().getPrimeiroNome(), MatchMode.START));
                }
                if (!GenericValidator.isBlankOrNull(pedido.getCliente().getUltimoNome())) {
                    criteria.add(Restrictions.like("cliente.ultimoNome", pedido.getCliente().getUltimoNome(), MatchMode.START));
                }
            }
            if (pedido.getStatus() != null) {
                criteria.add(Restrictions.eq("status", pedido.getStatus()));
            }

            if (pedido.getDataCompra() != null && !pedido.getDataCompra().equals(Constante.DATA_INICIAL)) {
                final Date dataTermino = new DateTime(pedido.getDataCompra().getTime())
                        .plusDays(1)
                        .toDate();
                criteria.add(Restrictions.ge("dataCompra", pedido.getDataCompra()))
                        .add(Restrictions.lt("dataCompra", dataTermino));

            }
        }

        return new ResultList<>(criteria, page);
    }
}
