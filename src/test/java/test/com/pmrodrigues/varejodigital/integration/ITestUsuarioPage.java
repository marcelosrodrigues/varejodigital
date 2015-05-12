package test.com.pmrodrigues.varejodigital.integration;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.MeusDadosPage;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.NovoUsuarioPage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

/**
 * Created by Marceloo on 05/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestUsuarioPage extends AbstractPage {


    @Test
    public void novo() throws Exception {

        NovoUsuarioPage novo = (NovoUsuarioPage) this.index()
                .to(NovoUsuarioPage.class);

        novo.nome("MARCELO DA SILVA RODRIGUES")
                .cpf("073.778.307-94")
                .email("lessarj@hotmail.com")
                .dataNascimento("10/02/1976")
                .estado("330")
                .cidade("RIO DE JANEIRO")
                .logradouro("ESTRADA CAMPO DA AREIA")
                .bairro("PECHINCHA")
                .numero("84")
                .cep("22743-310")
                .dddCelular("021")
                .numeroCelular("97105-9077")
                .desbloquear()
                .submit();

        long quantidade = jdbcTemplate.queryForObject("select count(id) from usuario where email = 'lessarj@hotmail.com'", Long.class);
        assertEquals(1L, quantidade);
    }

    @Test
    public void meusDados() throws Exception {

        final MeusDadosPage meusDados = (MeusDadosPage) this.index()
                .to(MeusDadosPage.class);


        jdbcTemplate.query("select * from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                assertEquals(rs.getString("nomeCompleto"), meusDados.getNome());
                assertEquals(rs.getString("cpf"), meusDados.getCPF());
                assertEquals(rs.getString("email"), meusDados.getEmail());
                assertEquals(rs.getString("cep"), meusDados.getCep());
                assertEquals(rs.getString("bairro"), meusDados.getBairro());
                assertEquals(rs.getString("cidade"), meusDados.getCidade());
                assertEquals(rs.getString("complemento"), meusDados.getComplemento());
                assertEquals(rs.getString("logradouro"), meusDados.getLogradouro());
                assertEquals(rs.getString("numero"), meusDados.getNumero());
                assertEquals(rs.getString("estado_id"), meusDados.getEstado());

                return null;
            }
        });

    }


    @After
    public void after() {
        super.after();

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'lessarj@hotmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                ITestUsuarioPage.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                ITestUsuarioPage.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }
        });
        this.jdbcTemplate.update("commit");
    }

}

