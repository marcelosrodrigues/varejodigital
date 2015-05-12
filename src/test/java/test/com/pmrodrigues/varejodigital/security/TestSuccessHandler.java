package test.com.pmrodrigues.varejodigital.security;

import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import com.pmrodrigues.varejodigital.security.SuccessHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

public class TestSuccessHandler {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private final UsuarioRepository repository = context.mock(UsuarioRepository.class);
    private final HttpServletRequest request = context.mock(HttpServletRequest.class);
    private final HttpServletResponse response = context.mock(HttpServletResponse.class);
    private final Authentication authentication = context.mock(Authentication.class);
    private final Usuario usuario = new Usuario();

    @Test
    public void testOnAuthenticationSuccess() throws Exception {

        usuario.setEmail("teste");

        context.checking(new Expectations() {{

            oneOf(authentication).getPrincipal();
            will(returnValue("teste"));

            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(repository).set(with(aNonNull(Usuario.class)));

            oneOf(request).getContextPath();
            will(returnValue(null));

            oneOf(response).sendRedirect(with(aNonNull(String.class)));
        }});

        final SuccessHandler handler = new SuccessHandler();
        final Field repository = handler.getClass().getDeclaredField("repository");
        repository.setAccessible(true);
        repository.set(handler, this.repository);
        handler.onAuthenticationSuccess(request, response, authentication);

    }

    @Test
    public void testOnAuthenticationSuccessComAcertoDeContexto() throws Exception {

        usuario.setEmail("teste");

        context.checking(new Expectations() {{

            oneOf(authentication).getPrincipal();
            will(returnValue("teste"));

            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(repository).set(with(aNonNull(Usuario.class)));

            allowing(request).getContextPath();
            will(returnValue("/teste"));

            oneOf(response).sendRedirect(with(aNonNull(String.class)));
        }});

        final SuccessHandler handler = new SuccessHandler();
        final Field repository = handler.getClass().getDeclaredField("repository");
        repository.setAccessible(true);
        repository.set(handler, this.repository);
        handler.onAuthenticationSuccess(request, response, authentication);

    }
}