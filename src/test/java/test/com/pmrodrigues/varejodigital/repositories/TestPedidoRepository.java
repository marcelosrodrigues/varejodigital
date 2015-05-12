package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.enumarations.StatusPagamento;
import com.pmrodrigues.varejodigital.models.Cliente;
import com.pmrodrigues.varejodigital.models.Pedido;
import com.pmrodrigues.varejodigital.repositories.PedidoRepository;
import com.pmrodrigues.varejodigital.repositories.ResultList;
import org.apache.james.mime4j.field.datetime.DateTime;
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

    @Test
    public void search() {

        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido", Long.class);
        Pedido pedido = new Pedido();
        pedido.setStatus(null);
        pedido.setDataCompra(null);
        final ResultList<Pedido> pedidos = repository.search(pedido);

        assertEquals(count, pedidos.getRecordCount());

    }

    @Test
    public void searchByPrimeiroNomeDoCliente() {
        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido inner join cliente  on pedido.cliente_id = cliente.id where cliente.primeiroNome like 'MARCELO%'", Long.class);

        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        cliente.setPrimeiroNome("MARCELO");
        pedido.setCliente(cliente);
        pedido.setStatus(null);

        final ResultList<Pedido> pedidos = repository.search(pedido);

        assertEquals(count, pedidos.getRecordCount());
    }

    @Test
    public void searchByUltimoNomeDoCliente() {
        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido inner join cliente  on pedido.cliente_id = cliente.id where cliente.ultimoNome like 'Tavares%'", Long.class);

        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        cliente.setUltimoNome("Tavares");
        pedido.setStatus(null);
        pedido.setCliente(cliente);

        final ResultList<Pedido> pedidos = repository.search(pedido);

        assertEquals(count, pedidos.getRecordCount());
    }

    @Test
    public void searchByStatusPedido() {
        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido where status = ?", Long.class, StatusPagamento.AGUARDANDO_PAGAMENTO);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
        pedido.setDataCompra(null);
        final ResultList<Pedido> pedidos = repository.search(pedido);

        assertEquals(count, pedidos.getRecordCount());
    }

    @Test
    public void searchByDataDaCompra() {

        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido where dataCompra >= '2015-01-25' and dataCompra < '2015-01-26'", Long.class);
        Pedido pedido = new Pedido();
        pedido.setDataCompra(DateTime.convertToDate(2015, 01, 25, 0, 0, 0, 0));
        pedido.setStatus(null);
        final ResultList<Pedido> pedidos = repository.search(pedido);

        assertEquals(count, pedidos.getRecordCount());

    }

    @Test
    public void testarPassandoValorNulo() {
        final Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido", Long.class);

        final ResultList<Pedido> pedidos = repository.search(null);

        assertEquals(count, pedidos.getRecordCount());
    }
}
