package test.com.pmrodrigues.varejodigital.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.controllers.LojaController;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.repositories.ShoppingRepository;
import com.pmrodrigues.varejodigital.sessionscope.Lojas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

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
    private Lojas lojas = new Lojas();

    @Before
    public void setup() {
        this.after();
        controller = new LojaController(repository, secaoRepository, lojas, result, validator);
    }

    @After
    public void after() {

        jdbcTemplate.query("select id from loja where nome = 'TESTE'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from areas_vendas where loja_id = ?", rs.getLong("id"));
                jdbcTemplate.update("delete from lojistas where loja_id = ?", rs.getLong("id"));
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

    @Test
    public void pesquisarLojasPeloNome() {

        final List<Loja> lojas = controller.pesquisarLojaPeloNome("TESTE");
        final Long count = jdbcTemplate.queryForObject("select count(1) from loja where nome like 'TESTE%'", Long.class);

        assertEquals(count, Long.valueOf(lojas.size()));
    }

    @Test
    public void adicionar() {

        final Long id = jdbcTemplate.queryForObject("select max(id) from loja", Long.class);
        final Loja loja = repository.findById(id);

        controller.adicionar(loja);

        assertTrue(!lojas.novos().isEmpty());

    }

    @Test
    public void remover() {
        final Long id = jdbcTemplate.queryForObject("select max(id) from loja", Long.class);
        final Loja loja = repository.findById(id);

        controller.remover(loja);

        assertTrue(!lojas.removidos().isEmpty());
    }


}
