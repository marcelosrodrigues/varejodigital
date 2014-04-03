package test.com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.FRANQUEADO;
import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.controllers.FranqueadoController;
import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.FranqueadoService;

public class TestFranqueadoController {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private FranqueadoService service;
	private TipoFranquiaRepository franquiaRepository;
	private EstadoRepository estadoRepository;
	private MockResult result;
	private FranqueadoController controller;

	@Before
	public void setup() {

		result = new MockResult();
		service = context.mock(FranqueadoService.class);
		franquiaRepository = context.mock(TipoFranquiaRepository.class);
		estadoRepository = context.mock(EstadoRepository.class);

		controller = new FranqueadoController(service, franquiaRepository,
				estadoRepository, result);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFormulario() {

		final List<Estado> estados = context.mock(List.class, LISTA_ESTADOS);
		final List<TipoFranquia> franquia = context.mock(List.class,
				LISTA_FRANQUIAS);

		context.checking(new Expectations() {
			{
				oneOf(estadoRepository).list();
				will(returnValue(estados));

				oneOf(franquiaRepository).list();
				will(returnValue(franquia));
			}
		});

		controller.iniciar();

		assertNotNull(result.included(LISTA_ESTADOS));
		assertNotNull(result.included(LISTA_FRANQUIAS));

	}

	@Test
	public void testSalvar() throws IndicacaoFranqueadoNaoEncontradoException,
			EstouroTamanhoDeRedeException {

		context.checking(new Expectations() {
			{
				oneOf(service).adicionar(with(aNonNull(Franqueado.class)),
						with(aNonNull(String.class)));
			}
		});

		controller.avancar(new Franqueado(), "", 1L);
		assertNotNull(result.included(FRANQUEADO));

	}

}
