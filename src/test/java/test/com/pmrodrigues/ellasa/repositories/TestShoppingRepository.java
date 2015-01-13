package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 02/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestShoppingRepository
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ShoppingRepository repository;

    private final Integer QUANTIDADE_MAXIMA_REGISTRO = 101;

    @Before
    public void before() {


        int id = this.jdbcTemplate.queryForObject("select max(id) + 1 from loja", Integer.class);

        jdbcTemplate.update("delete from loja where nome like 'TESTE%'");
        final List<Object[]> lojas = new ArrayList<>();
        for (int i = id; i < QUANTIDADE_MAXIMA_REGISTRO; i++) {
            lojas.add(new Object[]{i, String.format("TESTE_%d", i)});
        }

        jdbcTemplate.batchUpdate("insert into loja (id ,nome) values (?,?)", lojas);
    }

    @After
    public void after() {
        jdbcTemplate.update("delete from loja where nome like 'TESTE%'");
    }

    @Test
    public void search() {

        final ResultList<Loja> resultado = repository.search(null);
        final Long quantidade = jdbcTemplate.queryForObject("select count(1) from loja", Long.class);

        assertEquals(quantidade, resultado.getRecordCount());
        assertEquals(new Long(quantidade / Constante.TAMANHO_PAGINA), resultado.getPageCount());

        final List<Loja> lojas = resultado.getList();
        assertEquals(new Long(Constante.TAMANHO_PAGINA), new Long(lojas.size()));
    }

    @Test
    public void searchByName() {


        final Loja loja = new Loja();
        loja.setNome("TESTE_1");

        final ResultList<Loja> resultado = repository.search(loja);
        final Long quantidade = jdbcTemplate.queryForObject("select count(1) from loja where nome like 'TESTE_1%'", Long.class);

        assertEquals(quantidade, resultado.getRecordCount());

        Long paginas = quantidade / Constante.TAMANHO_PAGINA;
        if (quantidade % Constante.TAMANHO_PAGINA > 0) {
            paginas++;
        }
        assertEquals(paginas, resultado.getPageCount());

        final List<Loja> lojas = resultado.getList();

    }
}
