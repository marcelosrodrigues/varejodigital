package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.repositories.ResultList;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProdutoRepository extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProdutoRepository repository;

    @After
    public void after() {
        jdbcTemplate.update("delete from produto where nome = 'TESTE' and loja_id = 1");
    }

    @Test
    public void pesquisarProdutoPeloCodigo() {
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

        final Produto produto = repository.findByCodigoProduto(1L);
        assertNotNull(produto);
    }

    @Test
    public void listarProdutosPorLoja() {

        Loja loja = new Loja();
        loja.setId(1L);
        List<Produto> lojas = repository.listByLoja(loja);
        Long count = this.jdbcTemplate.queryForObject("select count(1) from produto where loja_id = ?", Long.class, 1L);
        assertEquals(count, Long.valueOf(lojas.size()));

    }

    @Test
    public void pesquisarProdutoExemploNulo() {
        ResultList<Produto> produtos = repository.search(null);
        assertNotNull(produtos);
        Long count = this.jdbcTemplate.queryForObject("select count(1) from produto", Long.class);
        assertEquals(count, produtos.getRecordCount());
    }

    @Test
    public void pesquisarProdutoPorLoja() {

        Produto produto = new Produto();
        Loja loja = new Loja();
        loja.setId(1L);
        produto.setLoja(loja);

        ResultList<Produto> produtos = repository.search(produto);
        assertNotNull(produtos);
        Long count = this.jdbcTemplate.queryForObject("select count(1) from produto where loja_id = ?", Long.class, 1L);
        assertEquals(count, produtos.getRecordCount());

    }

}
