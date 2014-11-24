package com.pmrodrigues.ellasa.rest;

import java.util.List;

import br.com.caelum.vraptor.*;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;

@Resource
public class ClienteController {

	private final ClienteRepository repository;
	
	private final Result result;
	
	private final static Logger logging = Logger.getLogger(ClienteController.class);
	
	public ClienteController(final ClienteRepository repository , final Result result ) {
		this.repository = repository;
		this.result = result;
	}

	@Get
	@Path("/clientes.json")
    @Consumes("application/json")
	public List<Cliente> clientes() {
		logging.debug("listando todos os clientes cadastrados no sistema");
		List<Cliente> clientes = repository.list();
		logging.debug("cliente encontrado com sucesso");
		
		result.use(Results.json())
			  .from(clientes)
			  .include("endereco","endereco.estado")
			  .exclude("endereco.estado.uf","endereco.estado.nome")
			  .serialize();
		
		
		return clientes;
	}

}
