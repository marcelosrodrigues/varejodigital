package test.com.pmrodrigues.ellasa.security;

import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import com.pmrodrigues.ellasa.security.HibernateRequestInterceptor;
import com.pmrodrigues.ellasa.services.UsuarioService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestHibernateRequestInterceptor extends AbstractTransactionalJUnit4SpringContextTests {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Resource(name = "userAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ShoppingRepository lojaRepository;

    @Autowired
    private UsuarioService service;

    private final InterceptorStack stack = context.mock(InterceptorStack.class);
    private final ResourceMethod method = context.mock(ResourceMethod.class);
    private final Object resourceInstance = context.mock(Object.class);
    private final HttpServletRequest request = context.mock(HttpServletRequest.class);

    @Before
    public void setup() {

        after();

    }

    @After
    public void after() {


        jdbcTemplate.update("update pedido set loja_id = 1 where loja_id = 2");

        jdbcTemplate.update("update produto set loja_id = 1 where loja_id = 2");

        jdbcTemplate.query("select id from usuario where email = 'barbara@projetandoo.com.br'", new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from membros where usuario_id = ?", rs.getLong("id"));
                jdbcTemplate.update("delete from lojistas where usuario_id = ?", rs.getLong("id"));
                return null;
            }
        });

        jdbcTemplate.update("delete from loja where id = 2");
    }


    @Test
    public void filtrarVendasPorVendedor() throws Exception {

        final Long perfil_id = jdbcTemplate.queryForObject("select id from perfil where nome = 'VENDEDOR'", Long.class);
        final Long usuario_id = jdbcTemplate.queryForObject("select id from usuario where email = 'barbara@projetandoo.com.br'", Long.class);

        jdbcTemplate.update("insert into membros ( usuario_id , perfil_id) values (?,?)", usuario_id, perfil_id);

        context.checking(new Expectations() {{

            oneOf(request).getRemoteUser();
            will(returnValue("barbara@projetandoo.com.br"));

            oneOf(request).isUserInRole("VENDEDOR");
            will(returnValue(Boolean.TRUE));

            allowing(stack);

        }});


        final HibernateRequestInterceptor interceptor = new HibernateRequestInterceptor(service, request);

        interceptor.intercept(stack, method, resourceInstance);

        final ResultList<Pedido> pedidos = repository.search(null);
        final Long count = jdbcTemplate.queryForObject("select count(1) from pedido where vendedor_id = 2", Long.class);

        assertEquals(count, pedidos.getRecordCount());

    }

    @Test
    public void filtrarVendasPorLoja() throws Exception {

        final Long perfil_id = jdbcTemplate.queryForObject("select id from perfil where nome = 'LOJISTA'", Long.class);
        final Long usuario_id = jdbcTemplate.queryForObject("select id from usuario where email = 'barbara@projetandoo.com.br'", Long.class);

        jdbcTemplate.update("insert into loja ( id , nome) values ( 2 , 'TESTE')");
        jdbcTemplate.update("insert into membros ( usuario_id , perfil_id) values (?,?)", usuario_id, perfil_id);
        jdbcTemplate.update("insert into lojistas ( usuario_id , loja_id) values ( ? , 2)", usuario_id);

        jdbcTemplate.query("select id from pedido order by id asc limit 0 , 3 ", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("update pedido set loja_id = 2 where id = ?", rs.getLong("id"));
                return null;
            }
        });


        context.checking(new Expectations() {{

            oneOf(request).getRemoteUser();
            will(returnValue("barbara@projetandoo.com.br"));

            oneOf(request).isUserInRole("VENDEDOR");
            will(returnValue(Boolean.FALSE));

            oneOf(request).isUserInRole("LOJISTA");
            will(returnValue(Boolean.TRUE));

            allowing(stack);

        }});

        final HibernateRequestInterceptor interceptor = new HibernateRequestInterceptor(service, request);

        interceptor.intercept(stack, method, resourceInstance);

        final ResultList<Pedido> pedidos = repository.search(null);
        final Long count = jdbcTemplate.queryForObject("select count(1) from pedido where loja_id = 2", Long.class);

        assertEquals(count, pedidos.getRecordCount());
    }

    @Test
    public void pesquisarApenasOsProdutosQueOUsuarioPodeVer() {
        final Long perfil_id = jdbcTemplate.queryForObject("select id from perfil where nome = 'LOJISTA'", Long.class);
        final Long usuario_id = jdbcTemplate.queryForObject("select id from usuario where email = 'barbara@projetandoo.com.br'", Long.class);

        jdbcTemplate.update("insert into loja ( id , nome) values ( 2 , 'TESTE')");
        jdbcTemplate.update("insert into membros ( usuario_id , perfil_id) values (?,?)", usuario_id, perfil_id);
        jdbcTemplate.update("insert into lojistas ( usuario_id , loja_id) values ( ? , 2)", usuario_id);

        jdbcTemplate.query("select id from produto order by id asc limit 0 , 3 ", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("update produto set loja_id = 2 where id = ?", rs.getLong("id"));
                return null;
            }
        });


        context.checking(new Expectations() {{

            oneOf(request).getRemoteUser();
            will(returnValue("barbara@projetandoo.com.br"));

            oneOf(request).isUserInRole("VENDEDOR");
            will(returnValue(Boolean.FALSE));

            oneOf(request).isUserInRole("LOJISTA");
            will(returnValue(Boolean.TRUE));

            allowing(stack);

        }});

        final HibernateRequestInterceptor interceptor = new HibernateRequestInterceptor(service, request);

        interceptor.intercept(stack, method, resourceInstance);

        final ResultList<Produto> produtos = produtoRepository.search(null);
        final Long count = jdbcTemplate.queryForObject("select count(1) from produto where loja_id = 2", Long.class);

        assertEquals(count, produtos.getRecordCount());
    }

    @Test
    public void pesquisarApenasAsLojasQueOUsuarioPodeVer() {
        final Long perfil_id = jdbcTemplate.queryForObject("select id from perfil where nome = 'LOJISTA'", Long.class);
        final Long usuario_id = jdbcTemplate.queryForObject("select id from usuario where email = 'barbara@projetandoo.com.br'", Long.class);

        jdbcTemplate.update("insert into loja ( id , nome) values ( 2 , 'TESTE')");
        jdbcTemplate.update("insert into membros ( usuario_id , perfil_id) values (?,?)", usuario_id, perfil_id);
        jdbcTemplate.update("insert into lojistas ( usuario_id , loja_id) values ( ? , 2)", usuario_id);


        context.checking(new Expectations() {{

            oneOf(request).getRemoteUser();
            will(returnValue("barbara@projetandoo.com.br"));

            oneOf(request).isUserInRole("VENDEDOR");
            will(returnValue(Boolean.FALSE));

            oneOf(request).isUserInRole("LOJISTA");
            will(returnValue(Boolean.TRUE));

            allowing(stack);

        }});

        final HibernateRequestInterceptor interceptor = new HibernateRequestInterceptor(service, request);

        interceptor.intercept(stack, method, resourceInstance);

        final ResultList<Loja> lojas = lojaRepository.search(null);
        final Long count = jdbcTemplate.queryForObject("select count(1) from loja where id = 2", Long.class);

        assertEquals(count, lojas.getRecordCount());
    }
}