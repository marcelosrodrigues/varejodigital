package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 14/10/2014.
 */
@Repository("PedidoRepository")
public class PedidoRepositoryImpl extends AbstractRepository<Pedido> implements PedidoRepository {
}
