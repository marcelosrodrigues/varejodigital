package test.com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.*;
import com.pmrodrigues.ellasa.repositories.*;
import com.pmrodrigues.ellasa.services.PagamentoFactory;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
public class TestPedidoService{

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private PedidoService service = new PedidoService();
    private ClienteRepository clienteRepository = context.mock(ClienteRepository.class);
    private TaxaRepository taxaRepository = context.mock(TaxaRepository.class);
    private PedidoRepository pedidoRepository = context.mock(PedidoRepository.class);
    private PagamentoFactory pagamentoService = context.mock(PagamentoFactory.class);
    private ProdutoRepository produtoRepository = context.mock(ProdutoRepository.class);
    private EstadoRepository estadoRepository = context.mock(EstadoRepository.class);
    private Pedido pedido = context.mock(Pedido.class);
    private Cliente cliente = new Cliente();
    private Cliente existed = new Cliente();
    private Estado estado = new Estado();
    private List<Taxa> taxas = context.mock(List.class,"taxas");
    private OrdemPagamento pagamento = context.mock(OrdemPagamento.class);

    @Before
    public void before() throws Exception {
       this.setField("clienteRepository",clienteRepository);
       this.setField("taxaRepository",taxaRepository);
       this.setField("pedidoRepository",pedidoRepository);
       this.setField("pagamentoService",pagamentoService);
       this.setField("produtoRepository",produtoRepository);
        this.setField("estadoRepository", estadoRepository);

        estado.setId(1L);
        existed.setEndereco(new EnderecoCliente());
        cliente.setEndereco(new EnderecoCliente());
        existed.getEndereco().setEstado(estado);
        cliente.getEndereco().setEstado(estado);

    }

    private void setField(String name , Object value ) throws Exception {

        Field atributo = service.getClass().getDeclaredField(name);
        atributo.setAccessible(true);
        atributo.set(service, value);

    }


    @Test
    public void efetuarPedido() {


        final Collection<ItemPedido> itens = new ArrayList<>();

        context.checking(new Expectations() {{
            allowing(pedido).getCliente();
            will(returnValue(cliente));

            oneOf(estadoRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(new Estado()));

            oneOf(pedido).getItens();
            will(returnValue(itens));

            allowing(pedido).setCodigoTransacao(with(aNonNull(String.class)));

            allowing(pedido).setEnderecoEntrega(with(aNonNull(EnderecoCliente.class)));

            oneOf(taxaRepository).list();
            will(returnValue(taxas));

            allowing(pedido).calcula(taxas);

            oneOf(pedidoRepository).add(pedido);

            allowing(pedido).getDadosPagamento();
            will(returnValue(pagamento));

            oneOf(pagamento).setPedido(pedido);

            oneOf(pagamentoService).pagar(pagamento);

            oneOf(pagamento).isSucesso();
            will(returnValue(Boolean.TRUE));

            oneOf(pedido).setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
        }});

        service.pagar(pedido);

    }

    @Test
    public void efetuarPedidoAtualizandoCliente() {

        cliente.setId(1L);
        final Collection<ItemPedido> itens = new ArrayList<>();

        context.checking(new Expectations() {{
            allowing(pedido).getCliente();
            will(returnValue(cliente));

            oneOf(estadoRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(new Estado()));

            oneOf(pedido).getItens();
            will(returnValue(itens));

            oneOf(clienteRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(existed));

            allowing(pedido).setCliente(existed);

            allowing(pedido).setCodigoTransacao(with(aNonNull(String.class)));

            allowing(pedido).setEnderecoEntrega(with(aNonNull(EnderecoCliente.class)));

            oneOf(taxaRepository).list();
            will(returnValue(taxas));

            allowing(pedido).calcula(taxas);

            oneOf(pedidoRepository).add(pedido);

            allowing(pedido).getDadosPagamento();
            will(returnValue(pagamento));

            oneOf(pagamento).setPedido(pedido);

            oneOf(pagamentoService).pagar(pagamento);

            oneOf(pagamento).isSucesso();
            will(returnValue(Boolean.TRUE));

            oneOf(pedido).setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
        }});


        service.pagar(pedido);

    }
}

