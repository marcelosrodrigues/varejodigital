package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.rest.PagamentoController;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Marceloo on 17/11/2014.
 */
public class TestPagamentoController {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private Result result = new MockResult();

    private PedidoService service = context.mock(PedidoService.class);

    private PagamentoController controller;

    private UsuarioRepository repository = context.mock(UsuarioRepository.class);

    @Test
    public void devePagar() {

        final Usuario usuario = new Usuario();
        final Pedido pedido = new Pedido();
        final Loja loja = new Loja();

        final Authentication user = context.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(user);

        final UserDetails details = context.mock(UserDetails.class);

        context.checking(new Expectations() {{

            oneOf(user).getPrincipal();
            will(returnValue(details));

            allowing(details).getUsername();
            will(returnValue(""));


            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(service).pagar(pedido);
        }});

        controller = new PagamentoController(service, repository, result);
        controller.pagar(loja, pedido);


    }

    @After
    public void after() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
