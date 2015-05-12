package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.varejodigital.annotations.CRUD;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Pedido;
import com.pmrodrigues.varejodigital.repositories.PedidoRepository;

/**
 * Created by Marceloo on 16/03/2015.
 */
@Resource
@CRUD
public class PedidoController extends AbstractCRUDController<Pedido> {

    protected PedidoController(final PedidoRepository repository, final Result result, final Validator validator) {
        super(repository, result, validator);

    }

}
