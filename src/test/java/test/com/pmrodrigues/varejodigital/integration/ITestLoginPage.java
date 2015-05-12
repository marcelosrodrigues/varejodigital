package test.com.pmrodrigues.varejodigital.integration;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.DashboardPage;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.LoginPage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marceloo on 26/12/2014.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestLoginPage extends
        AbstractTransactionalJUnit4SpringContextTests {

    private LoginPage login;

    @Before
    @Transactional
    public void setup() {

        prepare();
        final Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, " +
                        "                              cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                false,
                "marsilvarodrigues@gmail.com",
                "12345678",
                "456.718.757-14",
                DateTime.now().toDate(),
                "Pechincha",
                "RIO DE JANEIRO",
                "RJ", "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );

        this.jdbcTemplate.update("commit");

    }

    private void prepare() {

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                ITestLoginPage.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                ITestLoginPage.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

        });

    }

    @After
    public void after() {
        prepare();
    }

    @Test
    public void autenticar() throws Exception {

        login = new LoginPage("http://localhost:8080");
        DashboardPage dashboard = (DashboardPage) login.email("marsilvarodrigues@gmail.com")
                .password("12345678")
                .submit();
        dashboard.close();
    }
}
