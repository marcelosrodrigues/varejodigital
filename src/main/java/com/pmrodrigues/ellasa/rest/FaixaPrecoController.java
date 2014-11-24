package com.pmrodrigues.ellasa.rest;

import java.util.List;

import br.com.caelum.vraptor.*;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.FaixaPreco;
import com.pmrodrigues.ellasa.repositories.FaixaRepository;

@Resource
public class FaixaPrecoController {

	private final FaixaRepository repository;

	private final Result result;

	private static final Logger logging = Logger.getLogger(FaixaPrecoController.class);

	public FaixaPrecoController(final FaixaRepository repository,
			final Result result) {
		this.repository = repository;
		this.result = result;
	}

	@Get
	@Path("/faixas.json")
    @Consumes("application/json")
	public List<FaixaPreco> faixas() {

		logging.debug("listando as faixas cadastrados no prestashop");
		final List<FaixaPreco> faixas = repository.list();
		logging.debug("lista encontrada");
		result.use(Results.json())
			  .from(faixas)
			  .include("origem","destino")
			  .exclude("origem.nome","destino.nome","origem.uf","destino.uf")
			  .serialize();

		return faixas;
	}

}
