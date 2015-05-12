package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.models.Atributo;
import com.pmrodrigues.varejodigital.repositories.AtributoRepository;
import com.pmrodrigues.varejodigital.sessionscope.Atributos;

/**
 * Created by Marceloo on 09/03/2015.
 */
@Resource
public class AtributoController {
    private final Atributos atributos;
    private final Result result;
    private final AtributoRepository repository;

    public AtributoController(final Atributos atributos, final Result result, final AtributoRepository repository) {
        this.atributos = atributos;
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
