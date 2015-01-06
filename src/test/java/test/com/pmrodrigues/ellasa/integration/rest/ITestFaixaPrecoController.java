package test.com.pmrodrigues.ellasa.integration.rest;

import com.pmrodrigues.ellasa.models.FaixaPreco;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Marceloo on 29/12/2014.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestFaixaPrecoController extends AbstractTestRest<FaixaPreco> {

    @Test
    public void listAll() throws JSONException {

        List<FaixaPreco> faixas = this.list("http://localhost:8080/faixas.json");
        Long count = this.jdbcTemplate.queryForObject("select count(1) from faixa_preco",Long.class);

        assertFalse(faixas.isEmpty());
        assertEquals(count, new Long(faixas.size()));


    }
}
