package test.com.pmrodrigues.ellasa.controllers;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.pmrodrigues.ellasa.controllers.PagamentoCartaoCreditoController;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.ContratoService;

public class TestPagamentoCartaoCreditoController {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private TipoFranquiaRepository franquiaRepository;
	private EstadoRepository estadoRepository;
	private MeioPagamentoRepository meioPagamentoRepostory;
	private ContratoService contratoService;
	private MockResult result;
	private PagamentoCartaoCreditoController controller;
	private Validator validator;

	@Before
	public void setup() {

		result = new MockResult();
		franquiaRepository = context.mock(TipoFranquiaRepository.class);
		estadoRepository = context.mock(EstadoRepository.class);
		meioPagamentoRepostory = context.mock(MeioPagamentoRepository.class);
		contratoService = context.mock(ContratoService.class);
		validator = new MockValidator();
		controller = new PagamentoCartaoCreditoController(franquiaRepository,
				estadoRepository, meioPagamentoRepostory, contratoService,
				result, validator);

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
