package test.com.pmrodrigues.varejodigital.integration.webservices;

import com.pmrodrigues.varejodigital.models.Estoque;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.webservice.ProdutoWebService;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 20/05/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProdutoWebService extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProdutoWebService webservice;

    @Autowired
    private SessionFactory sessionFactory;


    @After
    public void after() {
        jdbcTemplate.update("delete from produto where nome = 'TESTE' and loja_id = 1");
    }

    @Test
    public void deveSalvarProduto() {

        final Produto produto = new Produto();
        final Loja loja = new Loja();
        loja.setId(1L);

        produto.setLoja(loja);
        produto.setId(1L);
        produto.setNome("TESTE");
        produto.setPreco(BigDecimal.ONE);
        produto.setCusto(BigDecimal.ONE);
        produto.setCodigoBarras("123");
        produto.setCodigoInterno(1L);
        produto.setCodigoExterno(1L);
        produto.setEstoque(new Estoque(1L,1L,1L,1L,1L));

        webservice.salvar(produto);

        final long count = jdbcTemplate.queryForObject("select count(1) from produto where nome = 'TESTE' and loja_id = 1",Long.class);
        assertEquals(1L, count);
    }

    @Test
    public void deveAtualizarProduto() {

        jdbcTemplate.update("insert into produto ( loja_id , id , nome , preco , preco_custo , codigo_barra , " +
                "   codigo_interno , codigo_externo , quantidade_min_estoque , quantidade_em_estoque, " +
                " quantidade_max_estoque , ponto_de_ressuprimento , ponto_de_reposicao) " +
                " values (? , ? , ? , ? , ? , ? ,? ,? , ? , ? , ? , ? , ?)",
                1L,
                1L,
                "TESTE",
                BigDecimal.ONE,
                BigDecimal.ONE,
                "123",
                1L,
                1L,
                1L,
                1L,
                1L,
                1L,
                1L);


        final Produto produto = new Produto();
        final Loja loja = new Loja();
        loja.setId(1L);

        produto.setLoja(loja);
        produto.setId(1L);
        produto.setNome("TESTE");
        produto.setPreco(BigDecimal.ONE);
        produto.setCusto(BigDecimal.ONE);
        produto.setCodigoBarras("123");
        produto.setCodigoInterno(1L);
        produto.setCodigoExterno(1L);
        produto.setEstoque(new Estoque(2L,2L,2L,2L,2L));

        webservice.salvar(produto);
        sessionFactory.getCurrentSession().flush();

        final long count = jdbcTemplate.queryForObject("select count(1) from produto where nome = 'TESTE' and loja_id = 1",Long.class);
        assertEquals(1L, count);

    }

}
