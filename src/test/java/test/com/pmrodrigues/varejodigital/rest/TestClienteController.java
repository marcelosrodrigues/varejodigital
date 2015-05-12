package test.com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.varejodigital.models.Cliente;
import com.pmrodrigues.varejodigital.repositories.ClienteRepository;
import com.pmrodrigues.varejodigital.rest.ClienteController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestClienteController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private final Result result = new MockResult();
    @Autowired
    private ClienteRepository repository;
    private ClienteController controller = null;

    @Before
    public void setup() {
        controller = new ClienteController(repository, result);
    }

    @Test
    public void listarCliente() {

        List<Cliente> clientes = controller.clientes();
        assertNotNull(clientes);

    }

}
