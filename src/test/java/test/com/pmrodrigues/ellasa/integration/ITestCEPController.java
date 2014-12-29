package test.com.pmrodrigues.ellasa.integration;

import com.pmrodrigues.ellasa.models.CEP;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import test.com.pmrodrigues.ellasa.integration.rest.AbstractTestRest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Marceloo on 29/12/2014.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestCEPController extends AbstractTestRest<CEP> {

    @Test
    public void testListAll() throws Exception {

        List<CEP> ceps = this.list("http://localhost:8080/cep.json","marsilvarodrigues@gmail.com","12345678");

        final Long count = this.jdbcTemplate.queryForObject("select count(1) from cep",Long.class);
        assertFalse(ceps.isEmpty());
        assertEquals(count , new Long(ceps.size()));

    }

}
