package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import com.pmrodrigues.ellasa.annotations.CRUD;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.Repository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;

/**
 * Created by Marceloo on 09/12/2014.
 */
@Component
@CRUD
public class LojaController extends AbstractCRUDController<Loja> {

    public LojaController(ShoppingRepository repository, Result result, Validator validator) {
        super(repository, result, validator);
    }
}
