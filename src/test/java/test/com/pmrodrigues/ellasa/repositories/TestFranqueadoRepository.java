package test.com.pmrodrigues.ellasa.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

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

	private void deleteDadosDeTeste() {

        this.jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'" , new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                TestFranqueadoRepository.this.jdbcTemplate.update("delete from ordempagamento where contrato_id = ( select id from contrato where franqueado_id = ?)" , userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from contrato where franqueado_id = ?" , userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from franqueadopessoafisica where id = ?", userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from franqueadopessoajuridica where id = ?", userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from franqueado where id = ?", userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from usuario where id = ?" , userId);
                TestFranqueadoRepository.this.jdbcTemplate.update("delete from telefone where id in (?,?)" , celularId , residencialId);

                return null;
            };
        });
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
