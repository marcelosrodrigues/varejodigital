package test.com.pmrodrigues.ellasa.integration.rest;

import com.pmrodrigues.ellasa.models.Secao;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 13/01/2015.
 */

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestSecaoController extends AbstractTestRest<Secao> {


    @Test
    public void listarSecoesPorLoja() throws JSONException {

        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final List<Secao> secoes = this.list(format("http://localhost:8080/%s/secoes.json", id), "marsilvarodrigues@gmail.com", "12345678");

        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao inner join areas_vendas on id = secao_id where produto_id = ? and pai_id is null", Long.class, id);
        assertEquals(count, Long.valueOf(secoes.size()));

    }

}
