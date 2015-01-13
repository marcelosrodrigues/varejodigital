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

import java.util.List;

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
}
