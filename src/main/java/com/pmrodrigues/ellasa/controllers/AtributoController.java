package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.ellasa.models.Atributo;
import com.pmrodrigues.ellasa.repositories.AtributoRepository;
import com.pmrodrigues.ellasa.sessionscope.Atributos;

/**
 * Created by Marceloo on 09/03/2015.
 */
@Resource
public class AtributoController {
    private final Atributos atributos;
    private final Validator validator;
    private final Result result;
    private final AtributoRepository repository;

    public AtributoController(final Atributos atributos, final Validator validator, final Result result, AtributoRepository repository) {
        this.atributos = atributos;
        this.validator = validator;
        this.result = result;
        this.repository = repository;
    }

    @Post
    @Path("/produto/tamanho/{tamanho}/adicionar.json")
    public void adicionar(final String tamanho) {
        this.atributos.adicionar(tamanho);

        result.use(Results.json())
                .from(atributos.getAtributos())
                .serialize();
    }

    @Post
    @Path("/produto/tamanho/{tamanho}/remover.json")
    public void remover(final String tamanho) {
        this.atributos.remover(tamanho);

        result.use(Results.json())
                .from(atributos.getAtributos())
                .serialize();
    }

    @Post
    @Path("/produto/tamanho/{tamanho.descricao}/{tamanho.id}/remover.json")
    public void remover(final Atributo tamanho) {
        this.repository.remove(tamanho);

        result.use(Results.json())
                .from("excluido")
                .serialize();
    }
}
