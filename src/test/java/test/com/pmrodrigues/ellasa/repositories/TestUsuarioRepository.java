package test.com.pmrodrigues.ellasa.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UsuarioRepository repository;

	@SuppressWarnings("deprecation")
	private void deleteDadosDeTeste() {

		if (this.jdbcTemplate.queryForInt(
				"select count(1) from usuario where email = ?",
				"marsilvarodrigues@gmail.com") > 0) {
			final Long id = this.jdbcTemplate.queryForLong(
					"select id from usuario where email = ?",
					"marsilvarodrigues@gmail.com");

			final Long celular_id = this.jdbcTemplate.queryForLong(
					"select celular_id from usuario where id = ?", id);
			final Long residencial_id = this.jdbcTemplate.queryForLong(
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

	@Before
	public void before() {

		deleteDadosDeTeste();

		final Usuario usuario = new Usuario();
		usuario.setEmail("marsilvarodrigues@gmail.com");
		repository.add(usuario);
	}

	@After
	public void after() {
		deleteDadosDeTeste();
	}

	@Test
	public void buscarUsuarioPorEmail() {
		final Usuario usuario = repository.findByEmail("marsilvarodrigues@gmail.com");
		Assert.assertNotNull(usuario);
	}
}
