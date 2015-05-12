package test.com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import com.pmrodrigues.varejodigital.rest.UsuarioController;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private UsuarioController service;

    @Autowired
    private UsuarioRepository repository;

    @Resource(name = "org.springframework.security.authenticationManager")
    private AuthenticationManager authenticationManager;

    @Before
    public void before() {

        prepare();
        Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                true,
                "marsilvarodrigues@gmail.com",
                "12345678",
                "456.718.757-14",
                DateTime.now().toDate(),
                "pechincha",
                "RIO DE JANEIRO",
                "RJ", "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );

    }

    private void prepare() {

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                TestUsuarioController.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                TestUsuarioController.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

        });

    }

    @After
    public void after() {
        prepare();
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void recuperarMeusDados() {


        service = new UsuarioController(repository, new MockResult());
        final Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marsilvarodrigues@gmail.com", "12345678"));
        SecurityContextHolder.getContext().setAuthentication(user);


        final Usuario usuario = service.meusDados();
        assertNotNull(usuario);


    }

}
