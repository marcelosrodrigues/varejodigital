package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;


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
}
