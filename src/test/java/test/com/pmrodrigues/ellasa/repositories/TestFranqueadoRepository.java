package test.com.pmrodrigues.ellasa.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestFranqueadoRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FranqueadoRepository repository;

	@Autowired
	private EstadoRepository estadoRepository;

	private Estado estado;

	@Before
	public void before() {

		estado = estadoRepository.findById(313L);
		deleteDadosDeTeste();
	}

	@SuppressWarnings("deprecation")
	private void deleteDadosDeTeste() {

		if (this.jdbcTemplate.queryForInt(
				"select count(1) from usuario where email = ?",
				"marsilvarodrigues@gmail.com") > 0) {
			Long id = this.jdbcTemplate.queryForLong(
					"select id from usuario where email = ?",
					"marsilvarodrigues@gmail.com");

			Long celular_id = this.jdbcTemplate.queryForLong(
					"select celular_id from usuario where id = ?", id);
			Long residencial_id = this.jdbcTemplate.queryForLong(
					"select residencial_id from usuario where id = ?", id);

			if (this.jdbcTemplate.queryForInt(
					"select count(id) from contrato where franqueado_id = ?",
					id) > 0) {
				Long contrato_id = this.jdbcTemplate.queryForLong(
						"select id from contrato where franqueado_id = ?", id);

				this.jdbcTemplate.update(
						"delete from ordempagamento where contrato_id = ?",
						contrato_id);
				this.jdbcTemplate.update(
						"delete from contrato where franqueado_id = ?", id);
			}
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
	public void deveEcontrarFranqueadoPeloCodigo() {

		final Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		final Franqueado founded = repository.findByCodigo(franqueado.getCodigo());

		Assert.assertNotNull(founded);
	}
}
