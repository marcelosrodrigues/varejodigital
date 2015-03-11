package test.com.pmrodrigues.ellasa.tasks;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.tasks.PedidoTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 11/03/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestPedidoTask extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PedidoTask task;


    @Before
    public void setup() {

        this.after();
        this.jdbcTemplate.update("update pedido set status = 1 , akatus_transaction_code = '126975C0-982E-477F-878E-D973611BD625' where id = 1");

    }

    @After
    public void after() {
        this.jdbcTemplate.update("update pedido set status = 4 where id = 1");
    }

    @Test
    public void update() {

        task.update();

        Long status = this.jdbcTemplate.queryForObject("select status from pedido where id = 1", Long.class);

        assertEquals(Long.valueOf(StatusPagamento.CANCELADO.ordinal()), status);

    }
}
