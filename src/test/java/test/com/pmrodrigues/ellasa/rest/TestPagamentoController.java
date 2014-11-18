package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.*;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.repositories.*;
import com.pmrodrigues.ellasa.rest.PagamentoController;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 17/11/2014.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestPagamentoController extends
        AbstractTransactionalJUnit4SpringContextTests {

    private Result result = new MockResult();

    @Autowired
    private PedidoService service;

    private PagamentoController controller;

    @Resource(name = "ClienteRepository")
    private ClienteRepository clienteRepository;

    @Resource(name = "ProdutoRepository")
    private ProdutoRepository produtoRepository;

    @Resource(name = "EstadoRepository")
    private EstadoRepository estadoRepository;

    @Resource(name = "ShoppingRepository")
    private ShoppingRepository lojaRepository;

    @Autowired
    private UsuarioRepository repository;

    @Resource(name = "org.springframework.security.authenticationManager")
    private AuthenticationManager authenticationManager;

    @Before
    public void  before() {
        controller = new PagamentoController(service, repository, result);
        final Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("elinaldo.mkt@hotmail.com","12345678"));
        SecurityContextHolder.getContext().setAuthentication(user);
    }

    @Test
    public void devePagar() {

        final Pedido pedido = new Pedido();
        final Loja loja = lojaRepository.findById(12L);
        pedido.setLoja(loja);

        pedido.setCliente(clienteRepository.findById(28L));
        pedido.setDadosPagamento(criarOrdemPagamento());

        final List<Produto> produtos = produtoRepository.listByLoja(loja);
        for( final Produto produto : produtos ){
            pedido.adicionar(produto);
        }

        controller.pagar(pedido);

        assertNotNull(pedido.getId());
        assertNotEquals(0L,pedido.getId().longValue());

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


    @After
    public void after() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
