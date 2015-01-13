package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Before;
import com.pmrodrigues.ellasa.annotations.CRUD;
import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;

/**
 * Created by Marceloo on 09/01/2015.
 */
@Component
@CRUD
public class SecaoController extends AbstractCRUDController<Secao> {

    public SecaoController(final SecaoRepository repository, final Result result, final Validator validator) {
        super(repository, result, validator);
    }

    @Before
    public void before() {

        final SecaoRepository repository = (SecaoRepository) this.getRepository();
        this.getResult().include(Constante.DEPARTAMENTOS, repository.listAll());

    }


    @Get
    @Path("/secao/{secao}/novo.do")
    @Tiles(definition = "formulario-template")
    public void adicionarSubCategoria(final Secao secao){
        this.before();
        final Secao object = new Secao();
        object.setPai(secao);
        this.getResult().include(Constante.OBJECT,object);
    }
}
