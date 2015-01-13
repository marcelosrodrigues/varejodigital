package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.SecaoController;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;


@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecaoController extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SecaoRepository repository;

    private MockResult result = new MockResult();
    private MockValidator validator = new MockValidator();
    private SecaoController controller;

    @Before
    public void before() {

        controller = new SecaoController(repository,result,validator);

    }

    @Test
    public void testAdicionarSubCategoria() throws Exception {

        final Secao secao = repository.findById(212L);
        controller.adicionarSubCategoria(secao);

        assertNotNull(result.included(Constante.DEPARTAMENTOS));
        assertNotNull(result.included(Constante.OBJECT));

    }
}