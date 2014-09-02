package com.pmrodrigues.ellasa.rest;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.CEP;
import com.pmrodrigues.ellasa.repositories.CEPRepository;

@Resource
public class CEPController {

	private final CEPRepository repository; //NOPMD
	
	private final Result result; //NOPMD
	
	private static final Logger logging = Logger.getLogger(CEPController.class); //NOPMD
	
	public CEPController( final CEPRepository repository , final Result result ) {
		this.repository = repository;
		this.result = result;
	}
	
	@Get
	@Path("/cep.json")
	public List<CEP> ceps() {
		
		logging.debug("listando os ceps cadastrados no prestashop");
		final List<CEP> ceps = repository.list();
		logging.debug("lista encontrada");
		result.use(Results.json()) //NOPMD
		  .from(ceps)
		  .recursive()
		  .serialize();
		
		
		return ceps;
	}

}
