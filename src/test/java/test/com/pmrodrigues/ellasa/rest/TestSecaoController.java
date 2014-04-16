package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.rest.SecaoController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecaoController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	private SecaoController service;

	@Autowired
	private SecaoRepository repository;
	
	@Before
	public void  setup() {
		service = new SecaoController(repository, new MockResult());
	}
	
	@Test
	public void deveListarTodasAsSecoes() {
		
		final List<Secao> secoes = service.secoes();
		assertFalse( secoes.isEmpty());
		
		
	}

}
