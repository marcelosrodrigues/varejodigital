package test.com.pmrodrigues.ellasa.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FranqueadoRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private Estado estado;

	@Before
	public void before() {

		session = sessionFactory.openSession();
		deleteDadosDeTeste();
		estado = (Estado) session.get(Estado.class, 313L);
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
	public void deveInserir() {

		final Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		Assert.assertNotSame(0L, franqueado.getId());
	}

	@Test
	public void deveAlterar() {

		final FranqueadoPessoaFisica franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		franqueado.setNomeCompleto("Marcelo");
		repository.set(franqueado);

	}

	@Test
	public void deveRemover() {

		final Franqueado franqueado = Factory.getStubFranqueado(estado);
		repository.add(franqueado);

		repository.remove(franqueado);

		final Franqueado isDeleted = (Franqueado) session.get(Franqueado.class,
				franqueado.getId());
		Assert.assertNull(isDeleted);
	}

	@Test
	public void deveEncontrarPelaChave() {

		final Franqueado franqueado = Factory.getStubFranqueado(estado);
		repository.add(franqueado);

		final Franqueado toFound = repository.findById(franqueado.getId());

		Assert.assertNotNull(toFound);
	}

	@Test
	public void deveListarTodosOsValores() {
		final Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		List<Franqueado> franqueados = repository.list();
		Assert.assertFalse(franqueados.isEmpty());
	}

	@Test
	public void naoDeveEncontrarPelaChave() {
		Assert.assertNull(repository.findById(0L));
	}

}
