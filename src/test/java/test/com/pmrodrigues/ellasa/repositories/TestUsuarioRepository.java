package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.sql.ResultSet;
import java.sql.SQLException;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UsuarioRepository repository;

	private void prepare() {

        this.jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'" , new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                TestUsuarioRepository.this.jdbcTemplate.update("delete from usuario where id = ?" , userId);
                TestUsuarioRepository.this.jdbcTemplate.update("delete from telefone where id in (?,?)" , celularId , residencialId);

                return null;
            };
        });

	}

	@Before
	public void before() {

		prepare();
        Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'",Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                true,
                "marsilvarodrigues@gmail.com",
                "123456",
                "070.323.277-02",
                DateTime.now().toDate(),
                "pechincha",
                "RIO DE JANEIRO",
                "RJ", "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );
	}

	@After
	public void after() {
		prepare();
	}

	@Test
	public void buscarUsuarioPorEmail() {
		final Usuario usuario = repository.findByEmail("marsilvarodrigues@gmail.com");
		Assert.assertNotNull(usuario);
	}

}
