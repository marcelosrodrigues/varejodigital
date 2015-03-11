package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Pedido;

import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
public interface PedidoRepository extends Repository<Pedido>{
    List<Pedido> listPedidosAguardandoPagamento();
}
