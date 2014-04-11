package test.com.pmrodrigues.ellasa.repositories;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UsuarioRepository repository;

	@Before
	public void before() {
		Usuario usuario = new Usuario();
		usuario.setEmail("marsilvarodrigues@gmail.com");
		repository.add(usuario);
	}

	@After
	public void after() {
		List<Usuario> usuarios = repository.list();
		for (Usuario usuario : usuarios) {
			repository.remove(usuario);
		}
	}

	@Test
	public void buscarUsuarioPorEmail() {
		Usuario usuario = repository.findByEmail("marsilvarodrigues@gmail.com");
		Assert.assertNotNull(usuario);
	}
}
