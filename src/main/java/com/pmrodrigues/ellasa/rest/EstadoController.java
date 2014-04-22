package com.pmrodrigues.ellasa.rest;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;

@Resource
public class EstadoController {

	private final EstadoRepository repository;
	
	private final Result result;
	
	private static final Logger logging = Logger.getLogger(EstadoController.class);
	
	public EstadoController( final EstadoRepository repository , final Result result ) {
		this.repository = repository;
		this.result = result;
	}
	
	@Get
	@Path("/estados.json")
	public List<Estado> estados() {
		
		logging.debug("listando os estados cadastrados no prestashop");
		final List<Estado> estados = repository.list();
		logging.debug("lista encontrada");
		result.use(Results.json())
		  .from(estados).serialize();
		
		
		return estados;
	}

}
