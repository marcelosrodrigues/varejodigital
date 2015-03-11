package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 25/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestClienteRepository
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ClienteRepository repository;

    @Before
    public void setup() {

        this.after();

        Long estado_id = jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);
        jdbcTemplate.update("insert into cliente ( dataAlteracao , dataCriacaco , email , primeiroNome , sexo , ultimoNome ) " +
                " values ( current_timestamp , current_timestamp , 'marcelosrodrigues@hotmail.com','MARCELO',1,'RODRIGUES')");

        Long cliente_id = jdbcTemplate.queryForObject("select id from cliente where email = 'marcelosrodrigues@hotmail.com'", Long.class);

        jdbcTemplate.update("insert into endereco ( bairro, celular, cep, cidade, dataAlteracao, dataCriacaco, logradouro, telefone, cliente_id, estado_id ) values " +
                "( 'PECHINCHA', '21981363699','22743310','RIO DE JANEIRO' , current_timestamp , current_timestamp , 'ESTRADA CAMPO DA AREIA 84 APTO 206' , '2133926222' , ? , ? )", cliente_id, estado_id);

    }

    @After
    public void after() {

        jdbcTemplate.query("select id from cliente where email = 'marcelosrodrigues@hotmail.com'", new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from endereco where cliente_id = ?", rs.getLong("id"));
                return null;
            }
        });
        jdbcTemplate.update("delete from cliente where email = 'marcelosrodrigues@hotmail.com'");


    }

    @Test
    public void deveRetornarClientePeloEmail() {

        Cliente cliente = repository.findByEmail("marcelosrodrigues@globo.com");
        assertNotNull(cliente);

    }
}
