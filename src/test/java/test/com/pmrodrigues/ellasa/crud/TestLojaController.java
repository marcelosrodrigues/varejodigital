package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.LojaController;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
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
 * Created by Marceloo on 22/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestLojaController extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private SecaoRepository secaoRepository;

    @Autowired
    private ShoppingRepository repository;

    private MockResult result = new MockResult();
    private MockValidator validator = new MockValidator();
    private LojaController controller;

    @Before
    public void setup() {
        this.after();
        controller = new LojaController(repository, secaoRepository, result, validator);
    }

    @After
    public void after() {

        jdbcTemplate.query("select id from loja where nome = 'TESTE'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from areas_vendas where produto_id = ?", rs.getLong("id"));
                return null;
            }
        });
        jdbcTemplate.update("delete from loja where nome = 'TESTE'");

    }

    @Test
    public void devePossuirListasSecoesNaTelaDeCadastro() {
        controller.formulario();
        assertNotNull(result.included(Constante.DEPARTAMENTOS));
    }

}
