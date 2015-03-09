package com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Resource
public class SecaoController {
	
	private final SecaoRepository repository;

	private final Result result;
	
	private static final Logger logging = Logger
			.getLogger(SecaoController.class);

	
	public SecaoController(final SecaoRepository repository , final Result result) {
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
                .include("pai")
                .serialize();

        return secoes;
    }

    @Get
    @Path("/secoes/{nome}/list.json")
    public List<Secao> pesquisarPorNome(final String nome) {
        logging.debug(format("pesquisando todas a secoes que comecem com %s", nome));
        final List<Secao> secoes = repository.listByNome(nome);
        logging.debug("lista carregada");

        result.use(Results.json())
                .from(secoes)
                .exclude("pai", "subsecoes")
                .serialize();

        return secoes;
    }

    @Get
    @Path("/secoes/{secao}/filhos/list.json")
    public Collection<Secao> listSubSecoes(final Secao secao) {

        final List<Secao> secoes = new ArrayList<>();
        secoes.addAll(secao.getSubsecoes());

        result.use(Results.json())
                .from(secoes)
                .exclude("pai", "subsecoes")
                .serialize();

        return secoes;
    }
}
