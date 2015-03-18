package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.ellasa.annotations.CRUD;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;

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
