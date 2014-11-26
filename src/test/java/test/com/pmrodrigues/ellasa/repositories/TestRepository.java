package test.com.pmrodrigues.ellasa.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
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

	private void deleteDadosDeTeste() {


        this.jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'" , new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                    final Long userId = rs.getLong("id");
                    final Long celularId = rs.getLong("celular_id");
                    final Long residencialId = rs.getLong("residencial_id");

                    TestRepository.this.jdbcTemplate.update("delete from ordempagamento where contrato_id = ( select id from contrato where franqueado_id = ?)" , userId);
                    TestRepository.this.jdbcTemplate.update("delete from contrato where franqueado_id = ?" , userId);
                    TestRepository.this.jdbcTemplate.update("delete from franqueadopessoafisica where id = ?", userId);
                    TestRepository.this.jdbcTemplate.update("delete from franqueadopessoajuridica where id = ?", userId);
                    TestRepository.this.jdbcTemplate.update("delete from franqueado where id = ?", userId);
                    TestRepository.this.jdbcTemplate.update("delete from usuario where id = ?" , userId);
                    TestRepository.this.jdbcTemplate.update("delete from telefone where id in (?,?)" , celularId , residencialId);

                    return null;
            };
        });

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
