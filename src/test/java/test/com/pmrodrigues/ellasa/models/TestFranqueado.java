package test.com.pmrodrigues.ellasa.models;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestFranqueado
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	private EntityManager entityManager;

	private Estado estado;

	@Before
	public void before() {

		deleteDadosDeTeste();
		estado = entityManager.find(Estado.class, "RJ");
	}

	@SuppressWarnings("deprecation")
	private void deleteDadosDeTeste() {

		if (this.jdbcTemplate.queryForInt(
				"select count(1) from usuario where email = ?",
				"marcelosrodrigues@globo.com") > 0) {
			Long id = this.jdbcTemplate.queryForLong(
					"select id from usuario where email = ?",
					"marcelosrodrigues@globo.com");

			Long celular_id = this.jdbcTemplate.queryForLong(
					"select celular_id from usuario where id = ?", id);
			Long residencial_id = this.jdbcTemplate.queryForLong(
					"select residencial_id from usuario where id = ?", id);

			this.jdbcTemplate.update(
					"delete from franqueadopessoafisica where id = ?", id);
			this.jdbcTemplate.update(
					"delete from franqueadopessoajuridica where id = ?", id);
			this.jdbcTemplate.update("delete from franqueado where id = ?", id);
			this.jdbcTemplate.update("delete from usuario where id = ?", id);
			this.jdbcTemplate.update("delete from telefone where id in (?,?)",
					celular_id, residencial_id);

		}
	}

	@After
	public void after() {
		deleteDadosDeTeste();

	}

	@Test
	public void gerarCodigoIdentificao() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);

		entityManager.persist(franqueado);

		Assert.assertNotNull(franqueado.getCodigo());
		Assert.assertNotNull(franqueado.getPassword());

	}

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
