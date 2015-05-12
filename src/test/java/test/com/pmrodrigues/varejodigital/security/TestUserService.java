package test.com.pmrodrigues.varejodigital.security;

import com.pmrodrigues.varejodigital.models.Perfil;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import com.pmrodrigues.varejodigital.services.UsuarioService;
import org.apache.commons.lang.RandomStringUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class TestUserService {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private final UsuarioService service = new UsuarioService();
    private UsuarioRepository repository;
    private Usuario usuario;

    @Before
    public void setup() throws Exception {

        this.repository = context.mock(UsuarioRepository.class);
        this.usuario = context.mock(Usuario.class);
        final Field repository = service.getClass().getDeclaredField(
                "repository");
        repository.setAccessible(true);
        repository.set(service, this.repository);

    }

    @Test
    public void deveEncontrarOUsuario() {

        final Set<Perfil> perfis = new HashSet<>();
        perfis.add(new Perfil("role1"));
        perfis.add(new Perfil("role2"));
        perfis.add(new Perfil("role3"));

        context.checking(new Expectations() {
            {

                oneOf(repository).findByEmail(with(aNonNull(String.class)));
                will(returnValue(usuario));

                oneOf(usuario).getRoles();
                will(returnValue(perfis));

                oneOf(usuario).getPassword();
                will(returnValue(RandomStringUtils.randomAlphanumeric(8)));

                oneOf(usuario).isBloqueado();
                will(returnValue(Boolean.FALSE));

            }
        });

        UserDetails usuario = service
                .loadUserByUsername("marcelosrodrigues@globo.com");
        assertNotNull(usuario);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void naoDeveEncontrarUsuario() {

        context.checking(new Expectations() {
            {

                oneOf(repository).findByEmail(with(aNonNull(String.class)));
                will(returnValue(null));

            }
        });

        service.loadUserByUsername("marcelosrodrigues@globo.com");
    }

    @Test
    public void incrementarNumeroDeTentativasFalhas() {

        context.checking(new Expectations() {
            {

                oneOf(repository).findByEmail(with(aNonNull(String.class)));
                will(returnValue(usuario));

                oneOf(usuario).incrementarTentativasFalhas();

                oneOf(repository).set(with(aNonNull(Usuario.class)));
            }
        });

        service.atualizarTentativasFalhas(new User("teste", "teste", Collections.EMPTY_LIST));

    }

}
