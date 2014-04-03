package com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.FRANQUEADO;
import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.pmrodrigues.ellasa.annotations.Tiles;
import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.FranqueadoService;

@Resource
public class FranqueadoController {

	private final FranqueadoService service;

	private final TipoFranquiaRepository franquiaRepository;

	private final EstadoRepository estadoRepository;

	private final Result result;

	public FranqueadoController(final FranqueadoService service,
			final TipoFranquiaRepository franquiaRepository,
			EstadoRepository estadoRepository, final Result result) {
		this.service = service;
		this.franquiaRepository = franquiaRepository;
		this.estadoRepository = estadoRepository;
		this.result = result;
	}

	@Get
	@Path("/seja-um-franqueado.html")
	@Tiles(definition = "seja-um-franqueado-template")
	public void iniciar() {

		result.include(LISTA_ESTADOS, estadoRepository.list());
		result.include(LISTA_FRANQUIAS, franquiaRepository.list());

	}

	@Path("/seja-um-franqueado.html")
	@Post
	public void avancar(final Franqueado franqueado, String indicacao,
			Long franquia) {

		try {
			service.adicionar(franqueado, indicacao);
			result.include(FRANQUEADO, franqueado);
		} catch (IndicacaoFranqueadoNaoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EstouroTamanhoDeRedeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
