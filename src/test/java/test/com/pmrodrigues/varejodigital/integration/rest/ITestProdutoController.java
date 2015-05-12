package test.com.pmrodrigues.varejodigital.integration.rest;

import com.pmrodrigues.varejodigital.models.Produto;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 16/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestProdutoController extends AbstractTestRest<Produto> {

    @Test
    public void listarProdutosPorLoja() throws JSONException {

        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final List<Produto> produtos = this.list(format("\"http://localhost:8080/varejo/%s/produtos.json", id), "marsilvarodrigues@gmail.com", "12345678");
        final Long count = this.jdbcTemplate.queryForObject("select count(1) from produto where loja_id = ?", Long.class, id);
        assertEquals(count, Long.valueOf(produtos.size()));

    }
}
