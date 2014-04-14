package test.com.pmrodrigues.ellasa.controllers;

import static com.pmrodrigues.ellasa.Constante.LISTA_ESTADOS;
import static com.pmrodrigues.ellasa.Constante.LISTA_FRANQUIAS;
import static com.pmrodrigues.ellasa.Constante.LISTA_MEIOS_PAGAMENTO;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.pmrodrigues.ellasa.controllers.FranqueadoPessoaJuridicaController;
import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Endereco;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaJuridica;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.Residencial;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.FranqueadoService;

public class TestFranqueadoPessoaJuridicaController {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private FranqueadoService service;
	private TipoFranquiaRepository franquiaRepository;
	private EstadoRepository estadoRepository;
	private MeioPagamentoRepository meioPagamentoRepostory;
	private MockResult result;
	private FranqueadoPessoaJuridicaController controller;
	private Validator validator;

	@Before
	public void setup() {

		result = new MockResult();
		service = context.mock(FranqueadoService.class);
		franquiaRepository = context.mock(TipoFranquiaRepository.class);
		estadoRepository = context.mock(EstadoRepository.class);
		meioPagamentoRepostory = context.mock(MeioPagamentoRepository.class);
		validator = new MockValidator();
		controller = new FranqueadoPessoaJuridicaController(service,
				franquiaRepository, estadoRepository, meioPagamentoRepostory,
				result, validator);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFormulario() {

		final List<Estado> estados = context.mock(List.class, LISTA_ESTADOS);
		final List<TipoFranquia> franquia = context.mock(List.class,
				LISTA_FRANQUIAS);
		final List<MeioPagamento> meiodepagamento = context.mock(List.class,
				LISTA_MEIOS_PAGAMENTO);

		context.checking(new Expectations() {
			{
				oneOf(estadoRepository).list();
				will(returnValue(estados));

				oneOf(franquiaRepository).list();
				will(returnValue(franquia));

				oneOf(meioPagamentoRepostory).list();
				will(returnValue(meiodepagamento));
			}
		});

		controller.iniciar();

		assertNotNull(result.included(LISTA_ESTADOS));
		assertNotNull(result.included(LISTA_FRANQUIAS));
		assertNotNull(result.included(LISTA_MEIOS_PAGAMENTO));

	}

	@Test
	public void testAvancar() throws IndicacaoFranqueadoNaoEncontradoException,
			EstouroTamanhoDeRedeException {

		final FranqueadoPessoaJuridica indicadopor = context
				.mock(FranqueadoPessoaJuridica.class);
		final TipoFranquia tipo = context.mock(TipoFranquia.class);
		final MeioPagamento meio = context.mock(MeioPagamento.class);
		final FranqueadoPessoaJuridica franqueado = context.mock(
				FranqueadoPessoaJuridica.class, "juridica");
		final Residencial residencial = context.mock(Residencial.class);

		context.checking(new Expectations() {
			{
				oneOf(franqueado).getEndereco();
				will(returnValue(new Endereco()));

				oneOf(franqueado).getEmail();
				will(returnValue("marcelosrodrigues@globo.com"));

				allowing(franqueado).getResidencial();
				will(returnValue(residencial));

				oneOf(residencial).getDdd();
				will(returnValue("021"));

				oneOf(residencial).getNumero();
				will(returnValue("33926222"));

				oneOf(meio).eCartao();
				will(returnValue(Boolean.TRUE));

				oneOf(tipo).getValor();
				will(returnValue(new BigDecimal(1)));
			}
		});

		controller
				.avancar(franqueado, new FranqueadoPessoaFisica(), tipo, meio);
	}

	@Test
	public void testAvancarPagamentoBoleto()
			throws IndicacaoFranqueadoNaoEncontradoException,
			EstouroTamanhoDeRedeException {

		final Franqueado indicadopor = context.mock(Franqueado.class);
		final TipoFranquia tipo = context.mock(TipoFranquia.class);
		final MeioPagamento meio = context.mock(MeioPagamento.class);
		final FranqueadoPessoaJuridica franqueado = context.mock(
				FranqueadoPessoaJuridica.class, "juridica");

		final Residencial residencial = context.mock(Residencial.class);

		context.checking(new Expectations() {
			{
				oneOf(franqueado).getEndereco();
				will(returnValue(new Endereco()));

				oneOf(franqueado).getEmail();
				will(returnValue("marcelosrodrigues@globo.com"));

				allowing(franqueado).getResidencial();
				will(returnValue(residencial));

				oneOf(residencial).getDdd();
				will(returnValue("021"));

				oneOf(residencial).getNumero();
				will(returnValue("33926222"));

				oneOf(meio).eCartao();
				will(returnValue(Boolean.FALSE));

				oneOf(tipo).getValor();
				will(returnValue(new BigDecimal(1)));

			}
		});

		controller
				.avancar(franqueado, new FranqueadoPessoaFisica(), tipo, meio);

	}

}
