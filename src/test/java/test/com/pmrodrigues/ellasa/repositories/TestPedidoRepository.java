package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestPedidoRepository extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PedidoRepository repository;

    @Test
    public void pesquisarTodosOsPedidoAguardandoPagamento() {

        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido where status = 1", Long.class);

        final List<Pedido> pedidos = repository.listPedidosAguardandoPagamento();

        assertEquals(count, Long.valueOf(pedidos.size()));
    }
}