package test.com.pmrodrigues.varejodigital.security;

import com.pmrodrigues.varejodigital.security.UserAuthenticationManager;
import com.pmrodrigues.varejodigital.services.UsuarioService;
import com.pmrodrigues.varejodigital.utilities.MD5;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;

public class TestUserAuthenticationManager {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private final UsuarioService service = context.mock(UsuarioService.class);

    private UserAuthenticationManager authenticationManager = new UserAuthenticationManager();

    @Before
    public void before() throws Exception {

        Field service = authenticationManager.getClass().getDeclaredField("usuarioService");
        service.setAccessible(true);
        service.set(authenticationManager, this.service);

    }

    @Test
    public void autenticacaoComSucesso() throws Exception {

        final UserDetails user = context.mock(UserDetails.class);

        context.checking(new Expectations() {{
            oneOf(service).loadUserByUsername(with(aNonNull(String.class)));
            will(returnValue(user));

            oneOf(user).isAccountNonLocked();
            will(returnValue(true));

            oneOf(user).getPassword();
            will(returnValue(MD5.encrypt("12345678")));

            allowing(user);
        }});

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com", "12345678"));
        assertNotNull(authentication);

    }

    @Test(expected = AuthenticationException.class)
    public void usuarioNaoEncontrado() throws Exception {

        context.checking(new Expectations() {{
            oneOf(service).loadUserByUsername(with(aNonNull(String.class)));
            will(throwException(new UsernameNotFoundException("")));
        }});

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com", "12345678"));
    }

    @Test(expected = AuthenticationException.class)
    public void usuarioInformandoSenhaInvalida() throws Exception {
        final UserDetails user = context.mock(UserDetails.class);

        context.checking(new Expectations() {{
            oneOf(service).loadUserByUsername(with(aNonNull(String.class)));
            will(returnValue(user));

            oneOf(user).isAccountNonLocked();
            will(returnValue(true));

            oneOf(user).getPassword();
            will(returnValue(MD5.encrypt("asdfghj")));

            oneOf(service).atualizarTentativasFalhas(user);

        }});

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com", "12345678"));
    }

    @Test(expected = AuthenticationException.class)
    public void usuarioBloqueado() throws Exception {
        final UserDetails user = context.mock(UserDetails.class);

        context.checking(new Expectations() {{
            oneOf(service).loadUserByUsername(with(aNonNull(String.class)));
            will(returnValue(user));

            oneOf(user).isAccountNonLocked();
            will(returnValue(false));

        }});

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com", "12345678"));
    }

}