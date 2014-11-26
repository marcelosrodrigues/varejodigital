package test.com.pmrodrigues.ellasa.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UsuarioRepository repository;

	private void deleteDadosDeTeste() {

        this.jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'" , new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                TestUsuarioRepository.this.jdbcTemplate.update("delete from ordempagamento where contrato_id = ( select id from contrato where franqueado_id = ?)" , userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from contrato where franqueado_id = ?" , userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from franqueadopessoafisica where id = ?", userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from franqueadopessoajuridica where id = ?", userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from franqueado where id = ?", userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from usuario where id = ?" , userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from telefone where id in (?,?)" , celularId , residencialId);

                return null;
            };
        });
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
