package com.pmrodrigues.ellasa.rest;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;

@Resource
public class ProdutoController {

	private final ProdutoRepository repository;
	
	private final Result result;
	
	private static final Logger logging = Logger.getLogger(ProdutoController.class);
	
	public ProdutoController(final ProdutoRepository repository , final Result result ) {
		this.repository = repository;
		this.result = result;
	}
	
	
	@Path("/produtos.json")
	@Get
	public List<Produto> produtos() {
		logging.debug("iniciando a leitura dos produtos cadastrados no prestashop");
		final List<Produto> produtos = repository.list();
		logging.debug("lista encontrada");
		
		result.use(Results.json())
			  .from(produtos)
			  .include("imagens","atributos","secao")
			  .serialize();
		
		return produtos;
	}

}
