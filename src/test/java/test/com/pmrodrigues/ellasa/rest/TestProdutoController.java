package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.rest.ProdutoController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProdutoController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private ProdutoController service;

    @Autowired
    private ProdutoRepository repository;

    @Before
    public void setup() {
        service = new ProdutoController(repository, new MockResult());
    }

    @Test
    public void deveListarTodasOsProdutosDeUmaDeterminadaLoja() {

        final Long count = this.jdbcTemplate
                .queryForObject("select count(1) from produto where loja_id = ?", Long.class, 1L);

        Loja loja = new Loja();
        loja.setId(1L);
        final List<Produto> produtos = service.produtos(loja);
        assertEquals(count, Long.valueOf(produtos.size()));
    }

}
