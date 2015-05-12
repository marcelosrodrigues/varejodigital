package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.annotations.Before;
import com.pmrodrigues.varejodigital.annotations.CRUD;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.repositories.ShoppingRepository;
import com.pmrodrigues.varejodigital.sessionscope.Lojas;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marceloo on 09/12/2014.
 */
@Component
@CRUD
public class LojaController extends AbstractCRUDController<Loja> {

    private final SecaoRepository secaoRepository;
    private final Lojas lojas;

    public LojaController(final ShoppingRepository repository, final SecaoRepository secaoRepository,
                          final Lojas lojas, final Result result, final Validator validator) {
        super(repository, result, validator);
        this.secaoRepository = secaoRepository;
        this.lojas = lojas;

    }

    @Before
    public void before() {
        this.getResult().include(Constante.DEPARTAMENTOS, secaoRepository.list());
    }

    @Get
    @Path("/loja/{loja}/list.json")
    public List<Loja> pesquisarLojaPeloNome(final String loja) {
        final List<Loja> lojas = ((ShoppingRepository) this.getRepository()).listByNome(loja);
        this.getResult().use(Results.json())
                .from(lojas)
                .include("id", "nome")
                .exclude("secoes")
                .serialize();

        return lojas;
    }

    @Post
    @Path("/loja/{loja}/adicionar.json")
    public Collection<Loja> adicionar(final Loja loja) {
        lojas.adicionar(loja);

        this.getResult().use(Results.json())
                .from(loja)
                .include("id", "nome")
                .exclude("secoes")
                .serialize();

        return lojas.novos();
    }

    @Post
    @Path("/loja/{loja}/remover.json")
    public Collection<Loja> remover(final Loja loja) {
        lojas.remover(loja);

        this.getResult().use(Results.json())
                .from(loja)
                .include("id", "nome")
                .exclude("secoes")
                .serialize();

        return lojas.removidos();
    }
}
