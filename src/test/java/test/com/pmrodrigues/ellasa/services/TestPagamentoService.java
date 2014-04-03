package test.com.pmrodrigues.ellasa.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Boleto;
import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.services.PagamentoFactory;
import com.pmrodrigues.ellasa.services.PagamentoService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPagamentoService extends AbstractJUnit4SpringContextTests {

	@Autowired
	private PagamentoFactory factory;

	@Test
	public void testarPagamentoBoleto() {

		final PagamentoService service = factory
				.getPagamentoService(new Boleto());

		final OrdemPagamento ordem = new OrdemPagamento();
		final Contrato contrato = new Contrato();
		final Estado estado = new Estado();
		estado.setUf("RJ");
		estado.setNome("Rio de Janeiro");
		contrato.setFranqueado(Factory.getStubFranqueado(estado));
		ordem.setContrato(contrato);
		ordem.setValor(new BigDecimal("1"));
		ordem.setDescricao("franquia");

		service.pagar(ordem);

		assertEquals("Aguardando Pagamento", ordem.getStatus());

	}
}
