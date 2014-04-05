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

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.controllers.FranqueadoController;
import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.ContratoService;
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
	private MeioPagamentoRepository meioPagamentoRepostory;
	private ContratoService contratoService;
	private MockResult result;
	private FranqueadoController controller;

	@Before
	public void setup() {

		result = new MockResult();
		service = context.mock(FranqueadoService.class);
		franquiaRepository = context.mock(TipoFranquiaRepository.class);
		estadoRepository = context.mock(EstadoRepository.class);
		meioPagamentoRepostory = context.mock(MeioPagamentoRepository.class);
		contratoService = context.mock(ContratoService.class);
		controller = new FranqueadoController(service, franquiaRepository,
				estadoRepository, meioPagamentoRepostory, contratoService,
				result);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFormulario() {

		final List<Estado> estados = context.mock(List.class, LISTA_ESTADOS);
		final List<TipoFranquia> franquia = context.mock(List.class,
				LISTA_FRANQUIAS);
		final List<MeioPagamento> meiodepagamento = context.mock(List.class,LISTA_MEIOS_PAGAMENTO);

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

		final Franqueado indicadopor = context.mock(Franqueado.class);
		final TipoFranquia tipo = context.mock(TipoFranquia.class);
		final MeioPagamento meio = context.mock(MeioPagamento.class);

		context.checking(new Expectations() {
			{
				oneOf(service).findByCodigo(with(aNonNull(String.class)));
				will(returnValue(indicadopor));

				oneOf(indicadopor).adicionar(with(aNonNull(Franqueado.class)));

				oneOf(franquiaRepository).findById(with(aNonNull(Long.class)));
				will(returnValue(tipo));

				oneOf(meioPagamentoRepostory).findById(
						with(aNonNull(Long.class)));
				will(returnValue(meio));

				oneOf(meio).eCartao();
				will(returnValue(Boolean.TRUE));

				oneOf(tipo).getValor();
				will(returnValue(new BigDecimal(1)));
			}
		});

		controller.avancar(new Franqueado(),
				"", 1L, 1L);
	}

	@Test
	public void testPagar() {

		final TipoFranquia franquia = context.mock(TipoFranquia.class);
		final MeioPagamento pagamento = context.mock(MeioPagamento.class);

		context.checking(new Expectations() {
			{

				oneOf(franquiaRepository).findById(with(aNonNull(Long.class)));
				will(returnValue(franquia));

				oneOf(meioPagamentoRepostory).findById(
						with(aNonNull(Long.class)));
				will(returnValue(pagamento));

				oneOf(franquia).getValor();
				will(returnValue(BigDecimal.ZERO));

				oneOf(contratoService).assinar(
						with(aNonNull(OrdemPagamento.class)));
			}
		});

		OrdemPagamentoCartaoCredito ordem = new OrdemPagamentoCartaoCredito();
		ordem.setContrato(new Contrato());
		controller.pagar(ordem, 1L, 1L);
	}

}
