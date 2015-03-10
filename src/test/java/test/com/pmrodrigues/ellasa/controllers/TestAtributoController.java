package test.com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.controllers.AtributoController;
import com.pmrodrigues.ellasa.models.Atributo;
import com.pmrodrigues.ellasa.repositories.AtributoRepository;
import com.pmrodrigues.ellasa.sessionscope.Atributos;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAtributoController {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };


    @Test
    public void adicionar() {

        final Atributos atributos = new Atributos();

        final AtributoController controller = new AtributoController(atributos, new MockValidator(), new MockResult(), null);
        controller.adicionar("A");
        controller.adicionar("B");
        controller.adicionar("C");

        assertEquals(3, atributos.getAtributos().size());

    }

    @Test
    public void remover() {

        final Atributos atributos = new Atributos();

        final AtributoController controller = new AtributoController(atributos, new MockValidator(), new MockResult(), null);
        controller.adicionar("A");
        controller.adicionar("B");
        controller.adicionar("C");

        assertEquals(3, atributos.getAtributos().size());

        controller.remover("A");

        assertEquals(2, atributos.getAtributos().size());
    }

    @Test
    public void removerAtributo() {
        final AtributoRepository repository = context.mock(AtributoRepository.class);
        final Atributos atributos = new Atributos();

        context.checking(new Expectations() {{
            oneOf(repository).remove(with(aNonNull(Atributo.class)));
        }});

        final AtributoController controller = new AtributoController(atributos, new MockValidator(), new MockResult(), repository);

        controller.remover(new Atributo());
    }
}