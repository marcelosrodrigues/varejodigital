package test.com.pmrodrigues.varejodigital.webservice;

import com.pmrodrigues.varejodigital.models.ItemPedido;
import com.pmrodrigues.varejodigital.models.Pedido;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.PedidoRepository;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.webservice.PedidoWebService;
import com.pmrodrigues.varejodigital.webservice.dto.PedidoRequestType;
import com.pmrodrigues.varejodigital.webservice.dto.PedidoResponseType;
import com.pmrodrigues.varejodigital.webservice.dto.Status;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 25/05/2015.
 */
public class TestPedidoWebService {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private PedidoRepository repository = context.mock(PedidoRepository.class);
    private ProdutoRepository produtoRepository = context.mock(ProdutoRepository.class);
    private final PedidoWebService webservice = new PedidoWebService();

    @Before
    public void setup() throws Exception {
      this.setField(webservice,"repository",repository);
        this.setField(webservice,"produtoRepository",produtoRepository);
    }

    @Test
    public void deveReceberAVenda() {

        final PedidoRequestType request = context.mock(PedidoRequestType.class);
        final Pedido pedido = context.mock(Pedido.class);
        final Collection<ItemPedido> itens = new ArrayList<>();
        final ItemPedido item = context.mock(ItemPedido.class);
        final Produto produto = context.mock(Produto.class);
        itens.addAll(Arrays.asList(item));

        context.checking(new Expectations(){{

            oneOf(request).novoPedido();
            will(returnValue(pedido));

            oneOf(pedido).getItens();
            will(returnValue(itens));

            oneOf(item).getProduto();
            will(returnValue(produto));

            oneOf(produto).getCodigoExterno();
            will(returnValue(0L));

            oneOf(produtoRepository).findByCodigoProduto(with(aNonNull(Long.class)));
            will(returnValue(produto));

            oneOf(item).setProduto(with(aNonNull(Produto.class)));

            oneOf(repository).add(with(aNonNull(Pedido.class)));
        }});

        final PedidoResponseType response = webservice.salvarVendas(request);
        assertNotNull(response);
        assertEquals(Status.SUCESSO , response.getStatus());
    }

    @Test
    public void produtoNaoCadastrado() {

        final PedidoRequestType request = context.mock(PedidoRequestType.class);
        final Pedido pedido = context.mock(Pedido.class);
        final Collection<ItemPedido> itens = new ArrayList<>();
        final ItemPedido item = context.mock(ItemPedido.class);
        final Produto produto = context.mock(Produto.class);
        itens.addAll(Arrays.asList(item));

        context.checking(new Expectations(){{

            oneOf(request).novoPedido();
            will(returnValue(pedido));

            oneOf(pedido).getItens();
            will(returnValue(itens));

            oneOf(item).getProduto();
            will(returnValue(produto));

            oneOf(produto).getCodigoExterno();
            will(returnValue(0L));

            oneOf(produtoRepository).findByCodigoProduto(with(aNonNull(Long.class)));
            will(returnValue(null));

        }});

        final PedidoResponseType response = webservice.salvarVendas(request);
        assertNotNull(response);
        assertEquals(Status.ERROR , response.getStatus());
    }

    private void setField(Object object , String fieldname , Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldname);
        field.setAccessible(true);
        field.set(object,value);
    }

}