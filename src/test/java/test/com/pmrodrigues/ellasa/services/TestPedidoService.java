package test.com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.*;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.repositories.*;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Marceloo on 13/10/2014.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestPedidoService  extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource(name = "PedidoService")
    private PedidoService service;

    @Resource(name = "ClienteRepository")
    private ClienteRepository clienteRepository;

    @Resource(name = "ProdutoRepository")
    private ProdutoRepository produtoRepository;

    @Resource(name = "EstadoRepository")
    private EstadoRepository estadoRepository;

    @Resource(name = "ShoppingRepository")
    private ShoppingRepository lojaRepository;


    @Test
    @Transactional
    @Rollback(false)
    public void efetuarPedido() {

        Pedido pedido = new Pedido();
        Loja loja = lojaRepository.findById(12L);
        pedido.setLoja(loja);

        pedido.setCliente(clienteRepository.findById(28L));
        pedido.setDadosPagamento(criarOrdemPagamento());

        List<Produto> produtos = produtoRepository.listByLoja(loja);
        for( Produto produto : produtos ){
            pedido.adicionar(produto);
        }

        service.efetuarPedido(pedido);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @Rollback(false)
    public void efetuarPedidoComClienteNovo() {

        Cliente cliente = new Cliente();

        cliente.setPrimeiroNome("MARCELO");
        cliente.setUltimoNome("RODRIGUES");
        cliente.setEmail("marcelosrodrigues@globo.com");
        cliente.setDataNascimento(DateTime.now().withYear(1977).toDate());

        EnderecoCliente endereco = new EnderecoCliente();
        endereco.setEstado(estadoRepository.findById(330L));
        endereco.setLogradouro("Estrada Campo do Areia, 84 apto 206");
        endereco.setBairro("Pechincha");
        endereco.setCidade("Rio de Janeiro");
        endereco.setCep("22743310");
        endereco.setTelefone("2133926222");
        endereco.setCelular("21981363699");
        cliente.setEndereco(endereco);

        Pedido pedido = new Pedido();
        Loja loja = lojaRepository.findById(12L);
        pedido.setLoja(loja);
        pedido.setCliente(cliente);
        pedido.setDadosPagamento(criarOrdemPagamento());

        List<Produto> produtos = produtoRepository.listByLoja(loja);
        for( Produto produto : produtos ){
            pedido.adicionar(produto);
        }

        service.efetuarPedido(pedido);

    }

    private OrdemPagamentoCartaoCredito criarOrdemPagamento() {
        final OrdemPagamentoCartaoCredito ordem = new OrdemPagamentoCartaoCredito();

        final MeioPagamento cartao = new MeioPagamento();
        cartao.setDescricao("Cartão Visa");
        cartao.setTipo(Transaction.PaymentMethod.CARTAO_VISA);
        ordem.setMeioPagamento(cartao);
        ordem.setValor(new BigDecimal("1"));

        ordem.setNumero("4012001038443335");
        ordem.setCodigoSeguranca("123");
        ordem.setPortador("MARCELO DA S RODRIGUES");
        ordem.setCPF("070.323.277-02");
        ordem.setTelefone("02133926222");

        DateTime dataexpiracao = new DateTime(2018, 5, 01, 0, 0);
        ordem.setDataExpiracao(dataexpiracao.toDate());

        return ordem;
    }
}

