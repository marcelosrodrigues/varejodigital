package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.rest.UsuarioController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	private UsuarioController service;

	@Autowired
	private UsuarioRepository repository;
	
	@Resource(name = "org.springframework.security.authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Before
	public void  before() {
		service = new UsuarioController(repository, new MockResult());
		final Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com","12345678"));
		SecurityContextHolder.getContext().setAuthentication(user);
	}
	
	@After
	public void after() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Test
	public void recuperarMeusDados() {
		final Usuario usuario = service.meusDados();
		assertNotNull(usuario);
		
		
	}

}
