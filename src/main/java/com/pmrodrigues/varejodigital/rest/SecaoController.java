package com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Secao;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.String.format;

@Resource
public class SecaoController {

    private static final Logger logging = Logger
            .getLogger(SecaoController.class);
    private final SecaoRepository repository;
    private final Result result;


    public SecaoController(final SecaoRepository repository, final Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/{loja}/secoes.json")
    public List<Secao> secoes(final Loja loja) {

        logging.debug("carregando todas as seções da loja " + loja);
        final List<Secao> secoes = repository.findByLoja(loja);
        logging.debug("lista carregada");

        result.use(Results.json())
                .from(secoes)
                .include("pai", "icone")
                .serialize();

        return secoes;
    }

    @Get
    @Path("/{loja}/secoes/{nome}/list.json")
    public List<Secao> pesquisarPorNome(final Loja loja, final String nome) {
        logging.debug(format("pesquisando todas a secoes que comecem com %s", nome));
        final List<Secao> secoes = repository.listByNome(loja, nome);
        logging.debug("lista carregada");

        result.use(Results.json())
                .from(secoes)
                .exclude("pai", "subsecoes", "icone")
                .serialize();

        return secoes;
    }

    @Get
    @Path("/{loja}/secoes/{secao}/filhos/list.json")
    public List<Secao> listSubSecoes(final Loja loja, final Secao secao) {

        final List<Secao> secoes = repository.listAllSubSecoesByLojaAndPai(loja, secao);

        result.use(Results.json())
                .from(secoes)
                .exclude("pai", "subsecoes", "icone")
                .serialize();

        return secoes;
    }
}
