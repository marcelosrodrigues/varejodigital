package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.rest.MeioPagamentoController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Marceloo on 02/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestMeioPagamentoController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MeioPagamentoRepository repository;

    private MeioPagamentoController controller;

    @Before
    public void before() {
        controller = new MeioPagamentoController(repository , new MockResult());
    }

    @Test
    public void list() {
        final List<MeioPagamento> list = controller.listar();
        final Integer quantidade = jdbcTemplate.queryForObject("select count(*) from meiopagamento",Integer.class);
        assertFalse(list.isEmpty());
        assertEquals(quantidade,new Integer(list.size()));
    }
}

