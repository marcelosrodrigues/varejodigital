package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Before;
import com.pmrodrigues.ellasa.annotations.CRUD;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;

/**
 * Created by Marceloo on 09/12/2014.
 */
@Component
@CRUD
public class LojaController extends AbstractCRUDController<Loja> {

    private final SecaoRepository secaoRepository;

    public LojaController(ShoppingRepository repository, SecaoRepository secaoRepository, Result result, Validator validator) {
        super(repository, result, validator);
        this.secaoRepository = secaoRepository;
    }

    @Before
    public void before() {
        this.getResult().include(Constante.DEPARTAMENTOS, secaoRepository.list());
    }

}
