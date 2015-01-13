package test.com.pmrodrigues.ellasa.integration;

import org.joda.time.DateTime;
import org.junit.Before;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import test.com.pmrodrigues.ellasa.integration.pageobjects.AbstractPageObject;
import test.com.pmrodrigues.ellasa.integration.pageobjects.DashboardPage;
import test.com.pmrodrigues.ellasa.integration.pageobjects.LoginPage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marceloo on 05/01/2015.
 */
public class AbstractPage extends
        AbstractTransactionalJUnit4SpringContextTests {

    private DashboardPage index;

    @Before
    @Transactional
    public void setup() throws Exception {

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
                "22743-310",
                "RIO DE JANEIRO",
                "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );

        this.jdbcTemplate.update("commit");

        this.index = (DashboardPage) new LoginPage("http://localhost:8080")
                .email("marsilvarodrigues@gmail.com")
                .password("12345678")
                .submit();

    }

    private void prepare() {

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                AbstractPage.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                AbstractPage.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

            ;
        });

    }

    public void after() {
        this.index.close();
        prepare();
    }

    public AbstractPageObject index() {
        return this.index;
    }
}