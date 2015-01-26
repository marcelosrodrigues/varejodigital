package test.com.pmrodrigues.ellasa.deserialization;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.http.ParanamerNameProvider;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.resource.DefaultResourceClass;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.deserialization.CustomJSONDeserialization;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.rest.PagamentoController;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TestCustomJSONDeserialization {

    private final String JSON = "{" +
            "    \"pedido\": {" +
            "        \"loja\": {" +
            "            \"id\": 12" +
            "        }," +
            "        \"itens\": [ {" +
            "                    \"produto\": {" +
            "                        \"id\": 132" +
            "                    }," +
            "                    \"quantidade\": 1" +
            "                }" +
            "            ," +
            "            {" +
            "                    \"produto\": {" +
            "                        \"id\": 133" +
            "                    }," +
            "                    \"quantidade\": 1" +
            "                }" +
            "        ]," +
            "        \"pagamento\": {" +
            "            \"numero\": \"4012001038443335\"," +
            "            \"codigosegura\": \"123\"," +
            "            \"dataExpiracao\": \"2018-06-01\"," +
            "            \"portador\": \"MARCELO DA SILVA RODRIGUES\"," +
            "            \"cpf\": \"070.323.277-02\"," +
            "            \"telefone\": \"2133926222\"," +
            "            \"meioPagamento\": {" +
            "                \"id\": 2" +
            "            }" +
            "        }," +
            "        \"cliente\": {" +
            "            \"primeiroNome\": \"MARCELO\"," +
            "            \"ultimoNome\": \"RODRIGUES\"," +
            "            \"email\": \"marcelosrodrigues@globo.com\"," +
            "            \"dataNascimento\": \"1977-08-17\"," +
            "            \"endereco\": {" +
            "                \"logradouro\": \"ESTRADA CAMPO DA AREIA, 84 APTO 206\"," +
            "                \"bairro\": \"PECHINCHA\"," +
            "                \"cep\": \"22743310\"," +
            "                \"cidade\": \"RIO DE JANEIRO\"," +
            "                \"telefone\": \"2133926222\"," +
            "                \"celular\": \"21981363699\"," +
            "                \"estado\": {" +
            "                    \"id\": 330" +
            "                }" +
            "            }" +
            "        }" +
            "    }" +
            "}";


    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Test
    public void testDeserialize() throws Exception {


        final TypeNameExtractor typeNameExtractor = context.mock(TypeNameExtractor.class);
        final XStreamBuilder xStreamBuilder = context.mock(XStreamBuilder.class);
        final PedidoService service = context.mock(PedidoService.class);
        final UsuarioRepository repository = context.mock(UsuarioRepository.class);
        final ParanamerNameProvider paramNameProvider = context.mock(ParanamerNameProvider.class);
        final Result result = new MockResult();

        context.checking(new Expectations() {{

            oneOf(paramNameProvider).parameterNamesFor(with(aNonNull(Method.class)));
            will(returnValue(new String[]{"loja", "pedido"}));

        }});


        CustomJSONDeserialization jsonDeserialization = new CustomJSONDeserialization(paramNameProvider, typeNameExtractor, xStreamBuilder);
        InputStream stream = new ByteArrayInputStream(JSON.getBytes(StandardCharsets.UTF_8));
        PagamentoController controller = new PagamentoController(service, repository, result);
        ResourceMethod resourceMethod = new DefaultResourceMethod(new DefaultResourceClass(controller.getClass()), controller.getClass().getMethod("pagar", Loja.class, Pedido.class));

        final Object[] params = jsonDeserialization.deserialize(stream, resourceMethod);

        assertNotNull(params);
        assertNotEquals(0, params.length);

        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Pedido) {
                Pedido pedido = (Pedido) params[i];
                assertNotNull(pedido.getCliente());
                assertNotNull(pedido.getDadosPagamento());
                assertNotNull(pedido.getItens());
                assertFalse(pedido.getItens().isEmpty());
                assertEquals(2, pedido.getItens().size());
            }
        }

    }
}