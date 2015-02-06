package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.ProdutoController;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import com.pmrodrigues.ellasa.sessionscope.Imagens;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestProdutoController {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private MockResult result = new MockResult();
    private MockValidator validation = new MockValidator();
    private ShoppingRepository shoppingRepository = context.mock(ShoppingRepository.class);
    private SecaoRepository secaoRepository = context.mock(SecaoRepository.class);
    private ProdutoRepository repository = context.mock(ProdutoRepository.class);

    @Test
    public void montarFormulario() {

        context.checking(new Expectations() {{
            oneOf(secaoRepository).list();
            will(returnValue(Collections.EMPTY_LIST));

            oneOf(shoppingRepository).list();
            will(returnValue(Collections.EMPTY_LIST));
        }});

        final ProdutoController controller = new ProdutoController(repository, shoppingRepository, secaoRepository, result, validation, new Imagens());
        controller.formulario();

        assertNotNull(result.included(Constante.DEPARTAMENTOS));
        assertNotNull(result.included(Constante.LOJAS));

    }

    @Test
    public void salvarProduto() {

        final Imagens imagens = context.mock(Imagens.class);
        final Set<String> arquivos = new HashSet<>();
        arquivos.addAll(Arrays.asList("arquivo.gif", "arquivo2.gif", "arquivo3.gif"));

        context.checking(new Expectations() {{
            oneOf(imagens).getArquivos();
            will(returnValue(arquivos));

            oneOf(repository).add(with(aNonNull(Produto.class)));

            oneOf(imagens).apagar();
        }});

        final ProdutoController controller = new ProdutoController(repository, shoppingRepository, secaoRepository, result, validation, imagens);
        Produto produto = new Produto();
        controller.salvar(produto);
        assertNotNull(result.included(Constante.SUCESSO));
        assertFalse(produto.getImagens().isEmpty());
    }
}