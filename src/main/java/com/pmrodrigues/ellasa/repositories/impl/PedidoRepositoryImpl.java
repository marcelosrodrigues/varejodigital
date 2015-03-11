package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
}
