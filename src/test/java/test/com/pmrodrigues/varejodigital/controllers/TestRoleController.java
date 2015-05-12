package test.com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.varejodigital.controllers.RoleController;
import com.pmrodrigues.varejodigital.models.Perfil;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.PerfilRepository;
import com.pmrodrigues.varejodigital.sessionscope.Membros;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRoleController {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private final PerfilRepository repository = context.mock(PerfilRepository.class);
    private final MockResult result = new MockResult();
    private final MockValidator validator = new MockValidator();
    private final Membros membros = new Membros();
    private final RoleController controller = new RoleController(repository, membros, result, validator);

    @Test
    public void adicionarUsuario() {
        controller.adicionar(new Usuario());
        assertFalse(membros.novos().isEmpty());
    }

    @Test
    public void removerUsuario() {
        final Usuario usuario = new Usuario();
        usuario.setId(1L);
        controller.adicionar(usuario);

        final Usuario remover = new Usuario();
        remover.setId(1L);
        controller.remover(remover);

        assertTrue(membros.novos().isEmpty());
    }

    @Test
    public void salvar() {

        final Perfil existed = new Perfil();
        existed.setId(1L);
        context.checking(new Expectations() {{

            oneOf(repository).findById(1L);
            will(returnValue(existed));

            oneOf(repository).set(with(aNonNull(Perfil.class)));
        }});

        final Usuario usuario = new Usuario();
        usuario.setId(1L);
        controller.adicionar(usuario);

        final Usuario remover = new Usuario();
        remover.setId(2L);
        controller.remover(remover);
        final Perfil perfil = new Perfil();
        perfil.setId(1L);
        controller.update(perfil);

    }
}