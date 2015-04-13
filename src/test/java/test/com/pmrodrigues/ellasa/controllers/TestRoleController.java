package test.com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.controllers.RoleController;
import com.pmrodrigues.ellasa.models.Perfil;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.PerfilRepository;
import com.pmrodrigues.ellasa.sessionscope.Membros;
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