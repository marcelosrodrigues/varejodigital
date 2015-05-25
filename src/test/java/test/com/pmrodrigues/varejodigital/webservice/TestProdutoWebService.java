package test.com.pmrodrigues.varejodigital.webservice;


import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.webservice.ProdutoWebService;
import com.pmrodrigues.varejodigital.webservice.dto.ProdutoResponseType;
import com.pmrodrigues.varejodigital.webservice.dto.Status;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 14/05/2015.
 */
public class TestProdutoWebService{

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private ProdutoRepository repository = context.mock(ProdutoRepository.class);

    @Test
    public void deveAdicionarProdutoNovo() throws Exception {

        final ProdutoWebService webservice = new ProdutoWebService();
        final Produto produto = context.mock(Produto.class);
        context.checking(new Expectations(){{

            oneOf(produto).getCodigoExterno();
            will(returnValue(1L));

            oneOf(repository).findByCodigoProduto(with(aNonNull(Long.class)));
            will(returnValue(null));

            oneOf(repository).add(with(aNonNull(Produto.class)));

        }});

        setField(webservice, "repository", repository);

        ProdutoResponseType response = webservice.salvar(produto);
        assertNotNull(response);
        assertEquals(Status.SUCESSO,response.getStatus());

    }

    @Test
    public void deveAtualizarProdutoJaCadastro() throws Exception {

        final Produto produto = new Produto();
        produto.setCodigoExterno(1L);
        final Produto existed = new Produto();
        final ProdutoWebService webservice = new ProdutoWebService();

        context.checking(new Expectations(){{

            oneOf(repository).findByCodigoProduto(with(aNonNull(Long.class)));
            will(returnValue(existed));

            oneOf(repository).set(existed);

        }});

        setField(webservice, "repository", repository);

        ProdutoResponseType response = webservice.salvar(produto);
        assertNotNull(response);
        assertEquals(Status.SUCESSO,response.getStatus());

    }

    private void setField(Object object , String fieldname , Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = ProdutoWebService.class.getDeclaredField(fieldname);
        field.setAccessible(true);
        field.set(object,value);
    }


}