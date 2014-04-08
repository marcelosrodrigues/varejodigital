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

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.FranqueadoController;
import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
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

		final FranqueadoPessoaFisica indicadopor = context.mock(FranqueadoPessoaFisica.class);
		final TipoFranquia tipo = context.mock(TipoFranquia.class);
		final MeioPagamento meio = context.mock(MeioPagamento.class);

		context.checking(new Expectations() {
			{
				oneOf(service).findByCodigo(with(aNonNull(String.class)));
				will(returnValue(indicadopor));

				oneOf(indicadopor).adicionar(with(aNonNull(FranqueadoPessoaFisica.class)));

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

		controller.avancar(new FranqueadoPessoaFisica(), "", 1L, 1L);
	}

	@Test
	public void testAvancarPagamentoBoleto()
			throws IndicacaoFranqueadoNaoEncontradoException,
			EstouroTamanhoDeRedeException {

		final FranqueadoPessoaFisica indicadopor = context.mock(FranqueadoPessoaFisica.class);
		final TipoFranquia tipo = context.mock(TipoFranquia.class);
		final MeioPagamento meio = context.mock(MeioPagamento.class);

		context.checking(new Expectations() {
			{
				oneOf(service).findByCodigo(with(aNonNull(String.class)));
				will(returnValue(indicadopor));

				oneOf(indicadopor).adicionar(with(aNonNull(FranqueadoPessoaFisica.class)));

				oneOf(franquiaRepository).findById(with(aNonNull(Long.class)));
				will(returnValue(tipo));

				oneOf(meioPagamentoRepostory).findById(
						with(aNonNull(Long.class)));
				will(returnValue(meio));

				oneOf(meio).eCartao();
				will(returnValue(Boolean.FALSE));

				oneOf(tipo).getValor();
				will(returnValue(new BigDecimal(1)));

			}
		});

		controller.avancar(new FranqueadoPessoaFisica(), "", 1L, 1L);

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

	@Test
	public void testConcluirPagamentoComBoleto() {

		final OrdemPagamento ordempagamento = context
				.mock(OrdemPagamento.class);
		final TipoFranquia franquia = context.mock(TipoFranquia.class);
		final Contrato contrato = context.mock(Contrato.class);
		final Franqueado franqueado = context.mock(FranqueadoPessoaFisica.class);
		final MeioPagamento meioPagamento = context.mock(MeioPagamento.class);


		context.checking(new Expectations() {
			{
				oneOf(ordempagamento).getContrato();
				will(returnValue(contrato));

				oneOf(contrato).getFranqueado();
				will(returnValue(franqueado));

				oneOf(contrato).getTipoFranquia();
				will(returnValue(franquia));

				oneOf(franquia).getValor();
				will(returnValue(BigDecimal.ZERO));

				oneOf(ordempagamento)
						.setDescricao(with(aNonNull(String.class)));
				oneOf(ordempagamento)
						.setValor(with(aNonNull(BigDecimal.class)));

				oneOf(ordempagamento).getMeioPagamento();
				will(returnValue(meioPagamento));

				oneOf(meioPagamento).getTipo();
				will(returnValue(PaymentMethod.BOLETO));

				oneOf(contratoService).assinar(ordempagamento);
				oneOf(ordempagamento).getDocumento();
				will(returnValue("https://dev.akatus.com/boleto/YmRiZjI3NTItZDU1Yi00MTQ2LWIzYTctYTZjNzA3ZDkyNGJk.html"));
			}
		});

		controller.concluir(ordempagamento);
		assertNotNull(result.included(Constante.BOLETO));
	}

	@Test
	public void testConcluirPagamentoComTEF() {

		final OrdemPagamento ordempagamento = context
				.mock(OrdemPagamento.class);
		final TipoFranquia franquia = context.mock(TipoFranquia.class);
		final Contrato contrato = context.mock(Contrato.class);
		final Franqueado franqueado = context.mock(FranqueadoPessoaFisica.class);
		final MeioPagamento meioPagamento = context.mock(MeioPagamento.class);

		context.checking(new Expectations() {
			{
				oneOf(ordempagamento).getContrato();
				will(returnValue(contrato));

				oneOf(contrato).getFranqueado();
				will(returnValue(franqueado));

				oneOf(contrato).getTipoFranquia();
				will(returnValue(franquia));

				oneOf(franquia).getValor();
				will(returnValue(BigDecimal.ZERO));

				oneOf(ordempagamento)
						.setDescricao(with(aNonNull(String.class)));
				oneOf(ordempagamento)
						.setValor(with(aNonNull(BigDecimal.class)));

				oneOf(ordempagamento).getMeioPagamento();
				will(returnValue(meioPagamento));

				oneOf(meioPagamento).getTipo();
				will(returnValue(PaymentMethod.TEF_ITAU));

				oneOf(contratoService).assinar(ordempagamento);
				oneOf(ordempagamento).getDocumento();
				will(returnValue(""));
			}
		});

		controller.concluir(ordempagamento);
		assertNotNull(result.included(Constante.TEF));
	}

}
