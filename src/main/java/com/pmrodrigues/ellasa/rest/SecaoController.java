package com.pmrodrigues.ellasa.rest;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;

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
	
	@Path("/secoes.json")
	@Get
	public List<Secao> secoes() {

		logging.debug("carregando a listagem de secoes");
		final List<Secao> secoes = repository.list();
		logging.debug("lista carregada");
		
		result.use(Results.json()).from(secoes).include("pai").serialize();
		
		return secoes;

	}

}
