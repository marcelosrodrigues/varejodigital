package test.com.pmrodrigues.ellasa.integration.rest;

import com.pmrodrigues.ellasa.models.Estado;
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
public class ITestEstadoController extends AbstractTestRest<Estado> {


    @Test
    public void listAll() throws JSONException {
        List<Estado> estados = this.list("http://localhost:8080/estados.json");
        Long count = this.jdbcTemplate.queryForObject("select count(1) from estado",Long.class);

        assertFalse(estados.isEmpty());
        assertEquals(count,new Long(estados.size()));
    }
}
