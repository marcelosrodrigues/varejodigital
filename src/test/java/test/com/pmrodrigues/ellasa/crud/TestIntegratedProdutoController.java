package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.controllers.ProdutoController;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import com.pmrodrigues.ellasa.sessionscope.Atributos;
import com.pmrodrigues.ellasa.sessionscope.Imagens;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marceloo on 28/03/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestIntegratedProdutoController extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProdutoRepository repository;

    private MockResult result = new MockResult();
    private MockValidator validation = new MockValidator();

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private SecaoRepository secaoRepository;

    @Before
    public void before() {

        jdbcTemplate.query("SELECT id from produto where nome = 'TESTE'", new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from imagem where produto_id = ?", rs.getLong("id"));
                jdbcTemplate.update("delete from atributo_produto where produto_id = ?", rs.getLong("id"));
                return null;
            }
        });
        jdbcTemplate.update("delete from produto where nome = 'TESTE'");

    }

    @After
    public void after() {
        this.before();
    }

    @Test
    public void adicionarProdutoComAtributo() {

        final Imagens imagens = new Imagens();
        final Atributos atributos = new Atributos();
        final ProdutoController controller = new ProdutoController(repository, shoppingRepository, secaoRepository, result, validation, imagens, atributos);

        final Loja loja = shoppingRepository.findById(1L);
        final Secao secao = secaoRepository.findById(153L);

        atributos.adicionar("A");
        atributos.adicionar("B");
        atributos.adicionar("C");

        final Produto produto = new Produto();
        produto.setLoja(loja);
        produto.setDescricao("TESTE");
        produto.setNome("TESTE");
        produto.setDescricaoBreve("TESTE");
        produto.setPeso(BigDecimal.ONE);
        produto.setPreco(BigDecimal.ONE);
        produto.setSecao(secao);

        controller.insert(produto);
    }
}
