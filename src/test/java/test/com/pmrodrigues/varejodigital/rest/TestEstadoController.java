package test.com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import com.pmrodrigues.varejodigital.rest.EstadoController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestEstadoController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private EstadoController service;

    @Autowired
    private EstadoRepository repository;

    @Before
    public void setup() {
        service = new EstadoController(repository, new MockResult());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void deveListarTodasAsSecoes() {
        final Long count = this.jdbcTemplate
                .queryForLong("select count(1) from estado");

        final List<Estado> estados = service.estados();
        assertEquals(count, Long.valueOf(estados.size()));


    }

}
