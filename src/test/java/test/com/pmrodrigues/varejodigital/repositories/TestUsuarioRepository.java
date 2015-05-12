package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioRepository
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UsuarioRepository repository;

    private void prepare() {

        this.jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                jdbcTemplate.update("delete from usuario where id = ?", userId);
                jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

        });

    }

    @Before
    public void before() {

        prepare();
        Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                true,
                "marsilvarodrigues@gmail.com",
                "123456",
                "456.718.757-14",
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

    @Test
    public void pesquisarUsuarioPorEmailOuNome() {
        final Long count = jdbcTemplate.queryForObject(
                "select count(1) from usuario where email like ? or nomeCompleto like ?",
                Long.class, "marcelo%", "marcelo%");

        final List<Usuario> usuario = repository.listByNomeOrEmail("marcelo");
        assertEquals(count, Long.valueOf(usuario.size()));
    }
}
