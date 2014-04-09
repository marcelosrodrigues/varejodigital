package test.com.pmrodrigues.ellasa.controllers;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.PagamentoController;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.services.ContratoService;

public class TestPagamentoController {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};


	private ContratoService service;
	private MockResult result;
	private PagamentoController controller;


	@Before
	public void setup() {

		result = new MockResult();
		service = context.mock(ContratoService.class);
		controller = new PagamentoController(service, result);

	}

	@Test
	public void testConcluirPagamentoComBoleto() {

		final OrdemPagamento ordempagamento = context
				.mock(OrdemPagamento.class);
		final TipoFranquia franquia = context.mock(TipoFranquia.class);
		final Contrato contrato = context.mock(Contrato.class);
		final Franqueado franqueado = context.mock(Franqueado.class);
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

				oneOf(service).assinar(ordempagamento);
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
		final Franqueado franqueado = context.mock(Franqueado.class);
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

				oneOf(service).assinar(ordempagamento);
				oneOf(ordempagamento).getDocumento();
				will(returnValue(""));
			}
		});

		controller.concluir(ordempagamento);
		assertNotNull(result.included(Constante.TEF));
	}
}
