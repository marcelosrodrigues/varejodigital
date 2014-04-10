package test.com.pmrodrigues.ellasa.controllers;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.pmrodrigues.ellasa.controller.sessionscope.OrdemPagamentoSession;
import com.pmrodrigues.ellasa.controllers.PagamentoCartaoCreditoController;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.services.ContratoService;

public class TestPagamentoCartaoCreditoController {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private ContratoService contratoService;
	private MockResult result;
	private PagamentoCartaoCreditoController controller;
	private OrdemPagamentoSession session;
	private Validator validator;

	@Before
	public void setup() {

		result = new MockResult();
		contratoService = context.mock(ContratoService.class);
		validator = new MockValidator();
		session = context.mock(OrdemPagamentoSession.class);
		controller = new PagamentoCartaoCreditoController(contratoService,
				result, validator, session);

	}


	@Test
	public void testPagar() {

		final OrdemPagamento ordem = context.mock(OrdemPagamento.class);
		final Contrato contrato = context.mock(Contrato.class);
		final Franqueado franqueado = context.mock(Franqueado.class);

		context.checking(new Expectations() {
			{

				oneOf(session).fromSession();
				will(returnValue(ordem));

				oneOf(contratoService).assinar(
						with(aNonNull(OrdemPagamento.class)));

				oneOf(ordem).getContrato();
				will(returnValue(contrato));

				oneOf(contrato).getFranqueado();
				will(returnValue(franqueado));
			}
		});

		controller.pagar(new OrdemPagamentoCartaoCredito());
	}

}
