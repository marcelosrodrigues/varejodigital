package test.com.pmrodrigues.ellasa.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestFranqueado
		extends
			AbstractTransactionalJUnit4SpringContextTests {


	@Test
	public void deveTransportarRede() throws EstouroTamanhoDeRedeException {

		Franqueado franqueado = new FranqueadoPessoaFisica();

		for (int i = 0; i < 625; i++) {
			franqueado.adicionar(new FranqueadoPessoaFisica());
		}

		assertEquals(625, franqueado.getQuantidadeMembros());
		assertEquals(5, franqueado.getRede().size());

		for (final Franqueado membro : franqueado.getRede()) {
			assertEquals(5, membro.getRede().size());
			for (final Franqueado sub : membro.getRede()) {
				assertEquals(5, sub.getRede().size());
			}
		}

	}

}
