package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;
import com.pmrodrigues.ellasa.rest.ClienteController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestClienteController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private ClienteRepository repository;
	
	private final Result result = new MockResult();
	
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
