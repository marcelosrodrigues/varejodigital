package test.com.pmrodrigues.ellasa.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;
import com.pmrodrigues.ellasa.services.PagamentoFactory;
import com.pmrodrigues.ellasa.services.PagamentoService;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestPagamentoService extends AbstractJUnit4SpringContextTests {

	@Autowired
	private PagamentoFactory factory;

	@Test
	public void testarPagamentoBoleto() {

		final PagamentoService service = factory
				.getPagamentoService(PaymentMethod.BOLETO);

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

	/*
	 * Visa 4012001038443335 05/2018 123
	 * 
	 * Mastercard 5453010000066167 05/2018 123
	 * 
	 * Amex 376449047333005 05/2018 1234
	 * 
	 * Elo 6362970000457013 05/2018 123
	 * 
	 * Diners 36490102462661 05/2018 123
	 */

	@Test
	public void testarPagamentoCartaoCredito() {

		final MeioPagamento cartao = new MeioPagamento();
		cartao.setDescricao("Cartão Visa");
		cartao.setTipo(PaymentMethod.CARTAO_VISA);
		final PagamentoService service = factory
				.getPagamentoService(PaymentMethod.CARTAO_VISA);

		final OrdemPagamentoCartaoCredito ordem = new OrdemPagamentoCartaoCredito();
		final Contrato contrato = new Contrato();
		final Estado estado = new Estado();

		ordem.setMeioPagamento(cartao);
		estado.setUf("RJ");
		estado.setNome("Rio de Janeiro");
		contrato.setFranqueado(Factory.getStubFranqueado(estado));
		ordem.setContrato(contrato);
		ordem.setValor(new BigDecimal("1"));
		ordem.setDescricao("franquia");

		ordem.setNumero("4012001038443335");
		ordem.setCodigoSeguranca("123");
		ordem.setPortador("MARCELO DA S RODRIGUES");
		ordem.setCPF("070.323.277-02");
		ordem.setTelefone("02133926222");

		DateTime dataexpiracao = new DateTime(2018, 5, 01, 0, 0);
		ordem.setDataExpiracao(dataexpiracao.toDate());

		service.pagar(ordem);
	}

	@Test
	public void testTEF() {

		final PagamentoService service = factory
				.getPagamentoService(PaymentMethod.TEF_ITAU);

		final MeioPagamento meiopagamento = new MeioPagamento();
		meiopagamento.setDescricao("TEF");
		meiopagamento.setTipo(PaymentMethod.TEF_ITAU);

		final OrdemPagamento ordem = new OrdemPagamento();
		ordem.setMeioPagamento(meiopagamento);
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
