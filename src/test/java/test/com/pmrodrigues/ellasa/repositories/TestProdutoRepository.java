package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProdutoRepository extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProdutoRepository repository;

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
